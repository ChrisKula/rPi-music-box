package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.christiankula.rpimusicbox.remote.R
import dagger.android.AndroidInjection

class MusicBoxDiscoveryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_box_discovery)
    }
}
