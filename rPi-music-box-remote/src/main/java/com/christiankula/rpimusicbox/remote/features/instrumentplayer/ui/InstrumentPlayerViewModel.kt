package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class InstrumentPlayerViewModel : ViewModel() {

    class Factory @Inject constructor() : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InstrumentPlayerViewModel() as T
        }
    }
}