package io.digitalheart.resume.features.resumepreview

import android.content.res.Resources
import com.nhaarman.mockitokotlin2.mock
import io.digitalheart.resume.R
import io.digitalheart.resume.network.ResumeService
import io.digitalheart.resume.utils.RxSchedulersOverrideRule
import org.amshove.kluent.shouldBeEqualTo
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

    private val resources: Resources by inject()
    private val service: ResumeService = mock()
    private val viewModel = ResumeViewModel(ResumeState(), resources, service)


    @Test
    fun fetchResume() {
    }

    @Test
    fun formatPeriod() {
        viewModel.formatPeriod("2017-09-18", "2018-11-01") shouldBeEqualTo "September 2017 - November 2018"
        viewModel.formatPeriod("2017-09-18", "") shouldBeEqualTo "September 2017 - ${resources.getString(R.string.now)}"
        viewModel.formatPeriod("", "2018-11-01") shouldBeEqualTo ""
        viewModel.formatPeriod("invalid", "2018-11-01") shouldBeEqualTo ""
        viewModel.formatPeriod("2017-09-18", "invalid") shouldBeEqualTo ""
    }

    @Test
    fun formatEducationSubtitle() {
        viewModel.formatEducationSubtitle(
            "Bachelor",
            "Computer Science"
        ) shouldBeEqualTo resources.getString(R.string.of, "Bachelor", "Computer Science")
        viewModel.formatEducationSubtitle("", "Computer Science") shouldBeEqualTo "Computer Science"
        viewModel.formatEducationSubtitle("Bachelor", "") shouldBeEqualTo "Bachelor"
        viewModel.formatEducationSubtitle("", "") shouldBeEqualTo ""
    }

    @Test
    fun formatWorkHeading() {
        viewModel.formatWorkHeading("Navenio", "Android Developer") shouldBeEqualTo resources.getString(
            R.string.at,
            "Android Developer",
            "Navenio"
        )
        viewModel.formatWorkHeading("", "Android Developer") shouldBeEqualTo "Android Developer"
        viewModel.formatWorkHeading("Navenio", "") shouldBeEqualTo "Navenio"
        viewModel.formatWorkHeading("", "") shouldBeEqualTo ""
    }
}