package rpimusicbox.features.discovery.usecase

import io.reactivex.Observable
import rpimusicbox.features.discovery.DISCOVERY_PERMISSION
import rpimusicbox.features.discovery.models.MusicBox
import rpimusicbox.libraries.permissions.PermissionsManager
import rpimusicbox.libraries.rxnearby.Endpoint
import rpimusicbox.libraries.rxnearby.RxNearby
import rpimusicbox.libraries.rxnearby.discovery.DiscoveryInitiated
import rpimusicbox.libraries.rxnearby.discovery.DiscoveryStarted
import rpimusicbox.libraries.rxnearby.discovery.EndpointFound
import rpimusicbox.libraries.rxnearby.discovery.EndpointLost
import javax.inject.Inject


class DiscoveryUsecase @Inject constructor(private val rxNearby: RxNearby,
                                           private val permissionsManager: PermissionsManager) {

    fun hasPermissionForDiscovery(): Boolean {
        return permissionsManager.hasPermission(DISCOVERY_PERMISSION)
    }

    fun observeMusicBoxDiscovery(): Observable<MusicBoxDiscoveryEvent> {
        return rxNearby.observeDiscovery()
                .map { event ->
                    return@map when (event) {
                        DiscoveryInitiated -> MusicBoxDiscoveryEvent.DiscoveryInitiated
                        DiscoveryStarted -> MusicBoxDiscoveryEvent.DiscoveryStarted
                        is EndpointFound -> MusicBoxDiscoveryEvent.MusicBoxFound(event.endpoint.toMusicBox())
                        is EndpointLost -> MusicBoxDiscoveryEvent.MusicBoxLost(event.id)
                    }
                }
    }
}

sealed class MusicBoxDiscoveryEvent {
    object DiscoveryInitiated : MusicBoxDiscoveryEvent()
    object DiscoveryStarted : MusicBoxDiscoveryEvent()
    data class MusicBoxFound(val foundMusicBox: MusicBox) : MusicBoxDiscoveryEvent()
    data class MusicBoxLost(val lostMusicBoxId: String) : MusicBoxDiscoveryEvent()
}

private fun Endpoint.toMusicBox(): MusicBox {
    return MusicBox(id, name, serviceId)
}
