package io.digitalheart.resume.features.resumepreview

import android.content.res.Resources
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.withState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.digitalheart.resume.R
import io.digitalheart.resume.models.Resume
import io.digitalheart.resume.network.ResumeService
import io.digitalheart.resume.utils.RxSchedulersOverrideRule
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ResumeViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    private val service: ResumeService = mock()
    private val resources: Resources by inject()

    private lateinit var viewModel: ResumeViewModel
    private val resume = Resume()

    @Test
    fun fetchResume_success() {
        val resumeSubject = SingleSubject.create<Resume>()
        whenever(service.fetch()).thenReturn(resumeSubject)

        viewModel = ResumeViewModel(ResumeState(), resources, service)
        viewModel.fetchResume()

        verify(service).fetch()

        withState(viewModel) { Assert.assertTrue(it.request is Loading) }

        resumeSubject.onSuccess(resume)

        withState(viewModel) {
            Assert.assertTrue(it.request is Success)
            Assert.assertEquals(it.request(), resume)
        }
    }

    @Test
    fun fetchResume_failure() {
        whenever(service.fetch()).thenReturn(Single.error(Exception("Server not found")))

        viewModel = ResumeViewModel(ResumeState(), resources, service)
        viewModel.fetchResume()

        verify(service).fetch()

        withState(viewModel) {
            Assert.assertTrue(it.request is Fail)
            Assert.assertEquals(it.request(), null)
        }
    }

    @Test
    fun formatPeriod() {
        viewModel = ResumeViewModel(ResumeState(), resources, service)
        with(viewModel) {
            formatPeriod("2017-09-18", "2018-11-01") shouldBeEqualTo "September 2017 - November 2018"
            formatPeriod("2017-09-18", "") shouldBeEqualTo "September 2017 - ${resources.getString(R.string.now)}"
            formatPeriod("", "2018-11-01") shouldBeEqualTo ""
            formatPeriod("invalid", "2018-11-01") shouldBeEqualTo ""
            formatPeriod("2017-09-18", "invalid") shouldBeEqualTo ""
        }
    }

    @Test
    fun formatEducationSubtitle() {
        viewModel = ResumeViewModel(ResumeState(), resources, service)
        with(viewModel) {
            formatEducationSubtitle("Bachelor", "Computer Science") shouldBeEqualTo resources.getString(
                R.string.of,
                "Bachelor",
                "Computer Science"
            )
            formatEducationSubtitle("", "Computer Science") shouldBeEqualTo "Computer Science"
            formatEducationSubtitle("Bachelor", "") shouldBeEqualTo "Bachelor"
            formatEducationSubtitle("", "") shouldBeEqualTo ""
        }
    }

    @Test
    fun formatWorkHeading() {
        viewModel = ResumeViewModel(ResumeState(), resources, service)
        with(viewModel) {
            formatWorkHeading("Navenio", "Android Developer") shouldBeEqualTo resources.getString(
                R.string.at,
                "Android Developer",
                "Navenio"
            )
            formatWorkHeading("", "Android Developer") shouldBeEqualTo "Android Developer"
            formatWorkHeading("Navenio", "") shouldBeEqualTo "Navenio"
            formatWorkHeading("", "") shouldBeEqualTo ""
        }
    }

}