package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.christiankula.rpimusicbox.remote.models.MusicBox
import javax.inject.Inject

class InstrumentPlayerViewModel(private val foundMusicBox: MusicBox) : ViewModel() {

    class Factory @Inject constructor() : ViewModelProvider.Factory {

        private lateinit var foundMusicBox: MusicBox

        fun init(foundMusicBox: MusicBox) {
            this.foundMusicBox = foundMusicBox
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InstrumentPlayerViewModel(foundMusicBox) as T
        }
    }
}
