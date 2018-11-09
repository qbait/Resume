package io.digitalheart.resume.features.resumepreview

import android.support.v4.app.FragmentActivity
import io.digitalheart.resume.network.ResumeService
import com.airbnb.mvrx.*
import io.digitalheart.resume.core.MvRxViewModel
import io.digitalheart.resume.models.Resume
import io.digitalheart.resume.utils.printDate
import io.digitalheart.resume.utils.toLocalDate
import org.koin.android.ext.android.inject

data class ResumeState(
    val request: Async<Resume> = Uninitialized
) : MvRxState

class ResumeViewModel(
    initialState: ResumeState,
    private val resumeService: ResumeService
) : MvRxViewModel<ResumeState>(initialState) {

    init {
        fetchResume()
    }

    fun fetchResume() = withState { state ->
        if (state.request is Loading) return@withState

        resumeService
            .fetch()
            .execute { copy(request = it) }
    }

    fun formatPeriod(startDate: String, endDate: String): String = "${startDate.toLocalDate().printDate()} - ${endDate.toLocalDate().printDate()}"
    fun formatEducationSubtitle(studyType: String, area: String): String {
        return "$studyType of $area"
    }

    fun formatWorkHeading(company: String, position: String): String {
        return "$company, $position"
    }

    companion object : MvRxViewModelFactory<ResumeState> {
        @JvmStatic
        override fun create(
            activity: FragmentActivity,
            state: ResumeState
        ): BaseMvRxViewModel<ResumeState> {
            val service: ResumeService by activity.inject()
            return ResumeViewModel(state, service)
        }
    }
}
