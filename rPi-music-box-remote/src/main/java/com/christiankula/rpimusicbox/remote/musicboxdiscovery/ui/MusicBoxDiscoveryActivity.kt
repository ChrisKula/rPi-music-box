package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.arch.lifecycle.Observer
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

        viewModel.stateLiveData.observe(this, Observer { state ->
            when (state) {
                is StartMusicBoxDiscovery -> replaceFragment(R.id.mainContent, StartMusicBoxDiscoveryFragment.newInstance(), StartMusicBoxDiscoveryFragment.TAG)

                is MusicBoxDiscoveryStarted -> {
                    //TODO Change to DiscoveringMusicBoxFragment
                }

                is MusicBoxDiscoveryFailed -> {
                    //TODO Change to MusicBoxDiscoveryFailedFragment
                }

                is MusicBoxFound -> {
                    //TODO Change to MusicBoxFoundFragment
                }
            }
        })
    }

    override fun onSearchMusicBoxButtonClick() {
        viewModel.onSearchMusicBoxButtonClicked()
    }
}
