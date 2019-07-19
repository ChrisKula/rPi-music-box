package rpimusicbox.features.discovery.ui.found

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_found_music_box.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

internal class FoundMusicBoxFragment : Fragment() {

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    private val navArgs: FoundMusicBoxFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_found_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectToMusicBoxButton.setOnClickListener { viewModel.onConnectToMusicBoxButtonClicked() }

        foundMusicBoxName.text = navArgs.foundMusicBoxName
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)
    }
}
