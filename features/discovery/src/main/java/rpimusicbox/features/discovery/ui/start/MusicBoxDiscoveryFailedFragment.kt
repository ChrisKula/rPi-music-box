package rpimusicbox.features.discovery.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_music_box_discovery_failed.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

internal class MusicBoxDiscoveryFailedFragment : Fragment() {

    companion object {
        val TAG: String = MusicBoxDiscoveryFailedFragment::class.java.simpleName

        fun newInstance(): MusicBoxDiscoveryFailedFragment = MusicBoxDiscoveryFailedFragment()
    }

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_box_discovery_failed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryMusicBoxSearchButton.setOnClickListener { viewModel.onRetryMusicBoxSearchButtonClicked() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)
    }
}
