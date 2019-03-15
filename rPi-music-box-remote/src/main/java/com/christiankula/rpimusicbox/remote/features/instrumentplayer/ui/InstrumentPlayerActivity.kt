package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.christiankula.rpimusicbox.remote.R
import com.christiankula.rpimusicbox.remote.models.MusicBox
import dagger.android.AndroidInjection

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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_player)
    }
}
