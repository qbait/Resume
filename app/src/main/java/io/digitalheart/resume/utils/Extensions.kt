package io.digitalheart.resume.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun ImageView.loadFromUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this)
}

fun LocalDate?.printDate(): String {
    if (this == null) return ""
    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy")
    return this.format(formatter)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }