package rpimusicbox.features.discovery.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_start_music_box_discovery.*
import rpimusicbox.features.discovery.R

internal class StartMusicBoxDiscoveryFragment : Fragment() {
    companion object {
        val TAG: String = StartMusicBoxDiscoveryFragment::class.java.simpleName

        fun newInstance(): StartMusicBoxDiscoveryFragment = StartMusicBoxDiscoveryFragment()
    }

    private var interactionListener: InteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_music_box_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMusicBoxButton.setOnClickListener { interactionListener?.onSearchMusicBoxButtonClick() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is InteractionListener) {
            interactionListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        interactionListener = null
    }

    fun setSearchMusicBoxButtonEnabled(isEnabled: Boolean) {
        searchMusicBoxButton.isEnabled = isEnabled
    }

    interface InteractionListener {
        fun onSearchMusicBoxButtonClick()
    }
}
