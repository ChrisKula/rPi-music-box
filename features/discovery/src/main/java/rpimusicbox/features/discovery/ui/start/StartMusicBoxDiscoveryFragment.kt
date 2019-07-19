package rpimusicbox.features.discovery.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_start_music_box_discovery.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState.MusicBoxDiscoveryInitiated
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState.StartMusicBoxDiscovery
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

internal class StartMusicBoxDiscoveryFragment : Fragment() {

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_music_box_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMusicBoxButton.setOnClickListener { viewModel.onSearchMusicBoxButtonClicked() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer { state ->
            when (state) {
                StartMusicBoxDiscovery -> searchMusicBoxButton.isEnabled = true

                MusicBoxDiscoveryInitiated -> searchMusicBoxButton.isEnabled = false

                // It will retrigger discovering
                MusicBoxDiscoveryState.MusicBoxDiscoveryRetried -> searchMusicBoxButton.performClick()
            }
        })
    }
}
