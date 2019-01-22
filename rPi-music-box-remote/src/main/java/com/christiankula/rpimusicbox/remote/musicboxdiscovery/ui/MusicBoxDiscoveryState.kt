package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import com.christiankula.rpimusicbox.remote.nearby.Endpoint

sealed class MusicBoxDiscoveryState

object StartMusicBoxDiscovery : MusicBoxDiscoveryState()
object MusicBoxDiscoveryStarted : MusicBoxDiscoveryState()
object MusicBoxDiscoveryFailed : MusicBoxDiscoveryState()

data class MusicBoxFound(val endpoint: Endpoint) : MusicBoxDiscoveryState()
