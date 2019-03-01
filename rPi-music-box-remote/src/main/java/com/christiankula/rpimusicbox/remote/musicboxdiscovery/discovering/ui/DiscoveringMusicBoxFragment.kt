package com.christiankula.rpimusicbox.remote.musicboxdiscovery.discovering.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.christiankula.rpimusicbox.remote.R
import kotlinx.android.synthetic.main.fragment_discovering_music_box.*

class DiscoveringMusicBoxFragment : Fragment() {

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

    private var interactionListener: InteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discovering_music_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discoveringMusicBoxCancelSearchButton.setOnClickListener { interactionListener?.onCancelMusicBoxSearchButtonClick() }

        // TODO Find a better UX implementation (ie a Snackbar maybe ?)
        arguments?.getString(LOST_MUSIC_BOX_NAME_KEY)?.let {
            Toast.makeText(requireContext(), getString(R.string.lost_music_box, it), Toast.LENGTH_LONG).show()
        }
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
        fun onCancelMusicBoxSearchButtonClick()
    }
}