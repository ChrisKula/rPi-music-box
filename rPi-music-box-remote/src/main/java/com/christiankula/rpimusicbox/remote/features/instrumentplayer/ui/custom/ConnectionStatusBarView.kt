package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import com.christiankula.rpimusicbox.remote.R
import kotlinx.android.synthetic.main.view_connection_status_bar.view.*
import java.util.concurrent.TimeUnit

private val STATUS_ICON_ROTATION_ANIMATION_DURATION_MS = TimeUnit.SECONDS.toMillis(1)
private val STATUS_ICON_ROTATION_ANIMATION_INTERPOLATOR = LinearInterpolator()

private val STATUS_ICON_ROTATION_ANIMATION = RotateAnimation(0.0f, 360.0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f).apply {
    duration = STATUS_ICON_ROTATION_ANIMATION_DURATION_MS
    interpolator = STATUS_ICON_ROTATION_ANIMATION_INTERPOLATOR
    repeatCount = Animation.INFINITE
}

class ConnectionStatusBarView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        inflateView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAtr: Int) : super(context, attrs, defStyleAtr) {
        inflateView(context)
    }

    private fun inflateView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_connection_status_bar, this)
    }

    fun setConnectionRequested(musicBoxName: String) {
        startStatusIconRotateAnimation()

        connectionStatusIcon.setImageResource(R.drawable.ic_connecting_white_24dp)
        connectionStatusIcon.visibility = View.VISIBLE

        val connectingToText = context.getString(R.string.instrument_player_connecting_to_music_box, musicBoxName)

        val start = connectingToText.length - musicBoxName.length
        val end = connectingToText.length

        connectionStatusText.text = connectingToText.toBoldItalic(start, end)
    }

    fun setConnectionInitiated(musicBoxName: String) {
        startStatusIconRotateAnimation()

        connectionStatusIcon.setImageResource(R.drawable.ic_connecting_white_24dp)
        connectionStatusIcon.visibility = View.VISIBLE

        val connectionInitiatedToText = context.getString(R.string.instrument_player_connection_initiated_with_music_box, musicBoxName)
        val start = connectionInitiatedToText.length - musicBoxName.length
        val end = connectionInitiatedToText.length

        connectionStatusText.text = connectionInitiatedToText.toBoldItalic(start, end)
    }

    fun setConnectionApproved(clientName: String) {
        connectionStatusIcon.apply {
            clearAnimation()
            setImageResource(R.drawable.ic_music_note_white_24dp)
            visibility = View.VISIBLE
        }

        val playingAsText = context.getString(R.string.instrument_player_you_re_playing_as, clientName)

        val start = playingAsText.length - clientName.length
        val end = playingAsText.length

        connectionStatusText.text = playingAsText.toBoldItalic(start, end)
    }

    fun clear() {
        connectionStatusIcon.apply {
            clearAnimation()
            setImageResource(0)
            visibility = View.GONE
        }

        connectionStatusText.text = ""
    }

    private fun startStatusIconRotateAnimation() {
        if (connectionStatusIcon.animation == null) {
            connectionStatusIcon.startAnimation(STATUS_ICON_ROTATION_ANIMATION)
        }
    }
}

private fun String.toBoldItalic(start: Int, end: Int): CharSequence {
    return SpannableString(this).apply { setSpan(StyleSpan(Typeface.BOLD_ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
}
