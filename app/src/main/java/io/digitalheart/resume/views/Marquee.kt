package io.digitalheart.resume.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import io.digitalheart.resume.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Marquee @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView

    init {
        inflate(context, R.layout.marquee, this)
        titleView = findViewById(R.id.title)
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        titleView.text = title
    }
}