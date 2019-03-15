package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import com.christiankula.rpimusicbox.remote.models.MusicBox

sealed class MusicBoxDiscoveryNavigation {
    data class NavigateToInstrumentPlayer(val musicBox: MusicBox) : MusicBoxDiscoveryNavigation()
}
