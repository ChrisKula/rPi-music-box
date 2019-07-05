package rpimusicbox.features.discovery.ui

import rpimusicbox.features.discovery.models.MusicBox

sealed class MusicBoxDiscoveryState {

    object StartMusicBoxDiscovery : MusicBoxDiscoveryState()

    /**
     * The music box discovery has been initiated but the device is not yet searching for a music box
     *
     * Followed by either [MusicBoxDiscoveryStarted] or [MusicBoxDiscoveryFailed]
     */
    object MusicBoxDiscoveryInitiated : MusicBoxDiscoveryState()

    /**
     * The music box discovery has been correctly started and the devices is searching for a music box
     */
    object MusicBoxDiscoveryStarted : MusicBoxDiscoveryState()

    /**
     * Something bad happened during music box discovery initialization
     */
    object MusicBoxDiscoveryFailed : MusicBoxDiscoveryState()

    /**
     * A music box has been found
     *
     * @param musicBox the found music box
     */
    data class MusicBoxFound(val musicBox: MusicBox) : MusicBoxDiscoveryState()

    /**
     * A previously discovered music box has been lost
     */
    data class MusicBoxLost(val musicBox: MusicBox) : MusicBoxDiscoveryState()
}
