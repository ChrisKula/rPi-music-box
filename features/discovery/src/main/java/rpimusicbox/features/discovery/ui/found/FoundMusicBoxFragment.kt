package rpimusicbox.features.discovery.ui.found

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_found_music_box.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

private const val FOUND_MUSIC_BOX_NAME_KEY = "FOUND_MUSIC_BOX_NAME"

internal class FoundMusicBoxFragment : Fragment() {

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateBundle(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_found_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectToMusicBoxButton.setOnClickListener { viewModel.onConnectToMusicBoxButtonClicked() }

        foundMusicBoxName.text = arguments!!.getString(FOUND_MUSIC_BOX_NAME_KEY)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)
    }

    private fun validateBundle(bundle: Bundle?) {
        if (bundle != null && bundle.getString(FOUND_MUSIC_BOX_NAME_KEY).isNullOrEmpty()) {
            throw IllegalStateException()
        }
    }
}
