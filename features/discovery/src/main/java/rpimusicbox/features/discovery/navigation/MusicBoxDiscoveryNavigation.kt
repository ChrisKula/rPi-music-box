package rpimusicbox.features.discovery.navigation

import rpimusicbox.core.actions.instrumentplayer.MusicBoxArgs

sealed class MusicBoxDiscoveryNavigation {
    data class NavigateToInstrumentPlayer(val musicBoxArgs: MusicBoxArgs) : MusicBoxDiscoveryNavigation()
}
