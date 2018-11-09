package io.digitalheart.resume.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import io.digitalheart.resume.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ExtendedRow @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val headingView: TextView
    private val titleView: TextView
    private val subtitleView: TextView

    init {
        inflate(context, R.layout.extended_row, this)
        headingView = findViewById(R.id.heading)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        orientation = VERTICAL
    }

    @TextProp
    fun setHeading(heading: CharSequence) {
        headingView.text = heading
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        titleView.text = title
    }

    @TextProp
    fun setSubtitle(subtitle: CharSequence?) {
        subtitleView.visibility = if (subtitle.isNullOrBlank()) View.GONE else View.VISIBLE
        subtitleView.text = subtitle
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }
}