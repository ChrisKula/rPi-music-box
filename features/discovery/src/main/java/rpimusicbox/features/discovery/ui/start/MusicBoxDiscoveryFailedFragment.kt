package rpimusicbox.features.discovery.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_music_box_discovery_failed.*
import rpimusicbox.features.discovery.R

internal class MusicBoxDiscoveryFailedFragment : Fragment() {

    companion object {
        val TAG: String = MusicBoxDiscoveryFailedFragment::class.java.simpleName

        fun newInstance(): MusicBoxDiscoveryFailedFragment = MusicBoxDiscoveryFailedFragment()
    }

    private var interactionListener: InteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_box_discovery_failed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryMusicBoxSearchButton.setOnClickListener { interactionListener?.onRetryMusicBoxSearchButtonClick() }
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

    interface InteractionListener {
        fun onRetryMusicBoxSearchButtonClick()
    }
}
