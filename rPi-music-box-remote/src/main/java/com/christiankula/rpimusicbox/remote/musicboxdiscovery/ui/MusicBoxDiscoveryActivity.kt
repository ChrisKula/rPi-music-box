package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.christiankula.rpimusicbox.remote.R
import com.christiankula.rpimusicbox.remote.extensions.replaceFragment
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.start.ui.StartMusicBoxDiscoveryFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MusicBoxDiscoveryActivity : AppCompatActivity(), StartMusicBoxDiscoveryFragment.InteractionListener {

    @Inject
    lateinit var viewModelFactory: MusicBoxDiscoveryViewModel.Factory

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_box_discovery)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusicBoxDiscoveryViewModel::class.java)

        replaceFragment(R.id.mainContent, StartMusicBoxDiscoveryFragment.newInstance(), StartMusicBoxDiscoveryFragment.TAG)
    }

    override fun onSearchMusicBoxButtonClick() {
        viewModel.onSearchMusicBoxButtonClicked()
    }
}
