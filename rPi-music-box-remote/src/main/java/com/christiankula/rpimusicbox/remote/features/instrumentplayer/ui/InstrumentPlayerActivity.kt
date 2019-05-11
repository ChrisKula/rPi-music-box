package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.christiankula.rpimusicbox.remote.R
import com.christiankula.rpimusicbox.remote.models.MusicBox
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_instrument_player.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val FOUND_MUSIC_BOX_EXTRA_KEY = "FOUND_MUSIC_BOX"

class InstrumentPlayerActivity : AppCompatActivity() {

    companion object {
        /**
         * Create an Intent to navigate to this Activity
         *
         * @param originContext the Context that originated the Intent
         * @param foundMusicBox the [MusicBox] that has been previously found that will be connected to,
         * as the connection happens in this [InstrumentPlayerActivity]
         */
        fun newIntent(originContext: Context, foundMusicBox: MusicBox): Intent {
            return Intent(originContext, InstrumentPlayerActivity::class.java)
                    .apply { putExtra(FOUND_MUSIC_BOX_EXTRA_KEY, foundMusicBox) }
        }
    }

    @Inject
    lateinit var viewModelFactory: InstrumentPlayerViewModel.Factory

    private lateinit var viewModel: InstrumentPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_player)

        requireArguments()

        viewModelFactory.init(getFoundMusicBoxFromIntent())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InstrumentPlayerViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer {
            return@Observer when (it) {
                is IdleState -> {
                    connectionStatusIcon.apply {
                        setImageResource(0)
                        visibility = View.GONE

                        clearAnimation()
                    }

                    status.text = ""
                }

                is ConnectionToMusicBoxRequested -> {
                    connectionStatusIcon.apply {
                        setImageResource(R.drawable.ic_connecting_white_24dp)
                        visibility = View.VISIBLE

                        startAnimation(RotateAnimation(0.0f,
                                -360.0f,
                                Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f).apply {
                            duration = TimeUnit.SECONDS.toMillis(1)
                            repeatCount = Animation.INFINITE
                            interpolator = LinearInterpolator()

                        })
                    }

                    val musicBoxName = it.serverClientRelation.serverMusicBox.name

                    val string = getString(R.string.instrument_player_connecting_to_music_box, musicBoxName)

                    val start = string.length - musicBoxName.length
                    val end = string.length

                    status.text = string.toBoldItalic(start, end)
                }

                is ConnectionToMusicBoxInitiated -> {
                    status.text = getString(R.string.instrument_player_connection_initiated_with_music_box,
                            it.serverClientRelation.serverMusicBox.name)
                }

                is ConnectionToMusicBoxApproved -> {
                    connectionStatusIcon.apply {
                        setImageResource(R.drawable.ic_music_note_white_24dp)
                        visibility = View.VISIBLE

                        clearAnimation()
                    }

                    val clientName = it.serverClientRelation.clientName

                    val playingAsText = getString(R.string.instrument_player_you_re_playing_as, clientName)

                    val start = playingAsText.length - clientName.length
                    val end = playingAsText.length

                    status.text = playingAsText.toBoldItalic(start, end)
                }

                is ConnectionToMusicBoxRejected -> TODO()

                is ConnectionToMusicBoxError -> TODO()

                is MusicBoxDisconnected -> TODO()

                null -> {
                    // Do nothing
                }
            }
        })
    }

    private fun requireArguments() {
        if (getFoundMusicBoxFromIntent() == null) {
            throw IllegalStateException("Can't start InstrumentPlayerActivity without providing a MusicBox")
        }
    }

    private fun getFoundMusicBoxFromIntent() = intent.getParcelableExtra<MusicBox>(FOUND_MUSIC_BOX_EXTRA_KEY)
}


private fun String.toBoldItalic(start: Int, end: Int): CharSequence {
    return SpannableString(this).apply { setSpan(StyleSpan(Typeface.BOLD_ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
}
