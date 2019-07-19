package rpimusicbox.features.discovery.ui.discovering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_discovering_music_box.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

internal class DiscoveringMusicBoxFragment : Fragment() {

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovering_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discoveringMusicBoxCancelSearchButton.setOnClickListener { viewModel.onCancelMusicBoxSearchButtonClicked() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState) //

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)
    }
}
