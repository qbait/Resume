package io.digitalheart.resume.features.resumepreview

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import io.digitalheart.resume.R
import io.digitalheart.resume.core.BaseFragment
import io.digitalheart.resume.core.simpleController
import io.digitalheart.resume.utils.snack
import io.digitalheart.resume.views.*


class ResumeFragment : BaseFragment() {

    private val viewModel: ResumeViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.asyncSubscribe(ResumeState::request, onFail = { error ->
            error.message?.let { view.snack(it) }
        })
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        val resume = state.request()
        if (resume == null) {
            loadingRow {
                id("loading")
            }
            viewModel.fetchResume()
            return@simpleController
        }

        with(resume.basics) {
            image {
                id("image")
                imageUrl(picture)
            }

            marquee {
                id("name")
                title(name)
            }

            basicRow {
                id("email")
                title(email)
            }

            basicRow {
                id("phone")
                title(phone)
            }

            basicRow {
                id("website")
                title(website)
            }

            marquee {
                id("summaryMarquee")
                title(getString(R.string.summary))
            }

            basicRow {
                id("summary")
                title(summary)
            }
        }

        marquee {
            id("experienceMarquee")
            title(getString(R.string.experience))
        }

        resume.work.forEach {
            basicRow {
                id(it.company)
                heading(viewModel.formatWorkHeading(it.company, it.position))
                title(viewModel.formatPeriod(it.startDate, it.endDate))
                subtitle(it.summary)
            }
        }

        marquee {
            id("educationMarquee")
            title(getString(R.string.education))
        }

        resume.education.forEach {
            basicRow {
                id(it.institution)
                heading(it.institution)
                title(viewModel.formatPeriod(it.startDate, it.endDate))
                subtitle(viewModel.formatEducationSubtitle(it.studyType, it.area))
            }
        }

        marquee {
            id("languagesMarquee")
            title(getString(R.string.languages))
        }

        resume.languages.forEach { language ->
            basicRow {
                id(language.language)
                title(language.language)
                subtitle(language.fluency)
            }
        }
    }
}