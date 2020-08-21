package io.digitalheart.resume.features.resumepreview

import android.content.res.Resources
import com.airbnb.mvrx.*
import io.digitalheart.resume.R
import io.digitalheart.resume.core.MvRxViewModel
import io.digitalheart.resume.models.Resume
import io.digitalheart.resume.network.ResumeService
import io.digitalheart.resume.utils.printDate
import io.digitalheart.resume.utils.toLocalDate
import org.koin.android.ext.android.inject
import org.threeten.bp.format.DateTimeParseException

data class ResumeState(
    val request: Async<Resume> = Uninitialized
) : MvRxState

class ResumeViewModel(
    initialState: ResumeState,
    private val resources: Resources,
    private val resumeService: ResumeService
) : MvRxViewModel<ResumeState>(initialState) {

    fun fetchResume() = withState { state ->
        if (state.request is Loading) return@withState

        resumeService
            .fetch()
            .execute { copy(request = it) }
    }

    fun formatPeriod(startDate: String, endDate: String): String {
        val prettyStartDate = try {
            val startLocalDate = startDate.toLocalDate()
            startLocalDate.printDate()
        } catch (exception: DateTimeParseException) {
            return ""
        }

        val prettyEndDate = if (endDate.isEmpty())
            resources.getString(R.string.now)
        else try {
            val endLocalDate = endDate.toLocalDate()
            endLocalDate.printDate()
        } catch (exception: DateTimeParseException) {
            return ""
        }

        return "$prettyStartDate - $prettyEndDate"
    }

    fun formatEducationSubtitle(studyType: String, area: String): String =
        when {
            studyType.isNotEmpty() && area.isNotEmpty() -> resources.getString(R.string.of, studyType, area)
            studyType.isEmpty() && area.isNotEmpty() -> area
            studyType.isNotEmpty() && area.isEmpty() -> studyType
            else -> ""
        }

    fun formatWorkHeading(company: String, position: String): String =
        when {
            company.isNotEmpty() && position.isNotEmpty() -> resources.getString(R.string.at, position, company)
            company.isEmpty() && position.isNotEmpty() -> position
            company.isNotEmpty() && position.isEmpty() -> company
            else -> ""
        }

    companion object : MvRxViewModelFactory<ResumeViewModel, ResumeState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: ResumeState
        ): ResumeViewModel {
            val service: ResumeService by viewModelContext.activity.inject()
            val resources: Resources by viewModelContext.activity.inject()
            return ResumeViewModel(state, resources, service)
        }
    }
}
