package com.christiankula.rpimusicbox.remote.musicboxdiscovery.foundmusicbox

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.christiankula.rpimusicbox.remote.R
import kotlinx.android.synthetic.main.fragment_found_music_box.*

class FoundMusicBoxFragment : Fragment() {
    companion object {
        val TAG: String = FoundMusicBoxFragment::class.java.simpleName

        private const val FOUND_MUSIC_BOX_NAME_KEY = "FOUND_MUSIC_BOX_NAME"

        fun newInstance(musicBoxName: String) = FoundMusicBoxFragment().apply {
            arguments = Bundle().apply {
                putString(FOUND_MUSIC_BOX_NAME_KEY, musicBoxName)
            }
        }
    }

    private var interactionListener: InteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateBundle(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_found_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectToMusicBoxButton.setOnClickListener { interactionListener?.onConnectToMusicBoxButtonClick() }

        foundMusicBoxName.text = arguments!!.getString(FOUND_MUSIC_BOX_NAME_KEY)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FoundMusicBoxFragment.InteractionListener) {
            interactionListener = context
        } else {
            throw RuntimeException("$context must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()

        interactionListener = null
    }

    interface InteractionListener {
        fun onConnectToMusicBoxButtonClick()
    }

    private fun validateBundle(bundle: Bundle?) {
        if (bundle != null && bundle.getString(FOUND_MUSIC_BOX_NAME_KEY).isNullOrEmpty()) {
            throw IllegalStateException()
        }
    }
}
