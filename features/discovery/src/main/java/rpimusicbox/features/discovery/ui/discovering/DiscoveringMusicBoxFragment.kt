package rpimusicbox.features.discovery.ui.discovering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_discovering_music_box.*
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryViewModel

internal class DiscoveringMusicBoxFragment : Fragment() {

    companion object {
        val TAG: String = DiscoveringMusicBoxFragment::class.java.simpleName

        private const val LOST_MUSIC_BOX_NAME_KEY = "LOST_MUSIC_BOX_NAME"

        fun newInstance(): DiscoveringMusicBoxFragment = DiscoveringMusicBoxFragment()

        fun newInstance(lostMusicBoxName: String): DiscoveringMusicBoxFragment = DiscoveringMusicBoxFragment().apply {
            arguments = Bundle().apply {
                putString(LOST_MUSIC_BOX_NAME_KEY, lostMusicBoxName)
            }
        }
    }

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovering_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discoveringMusicBoxCancelSearchButton.setOnClickListener { viewModel.onCancelMusicBoxSearchButtonClicked() }

        // TODO Find a better UX implementation (ie a Snackbar maybe ?)
        arguments?.getString(LOST_MUSIC_BOX_NAME_KEY)?.let {
            Toast.makeText(requireContext(), getString(R.string.lost_music_box, it), Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState) //

        viewModel = ViewModelProviders.of(requireActivity()).get(MusicBoxDiscoveryViewModel::class.java)
    }
}
