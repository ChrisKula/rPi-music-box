package rpimusicbox.libraries.rxnearby.discovery

import rpimusicbox.libraries.commons.rxjava2.SimpleDisposable
import rpimusicbox.libraries.rxnearby.Endpoint
import rpimusicbox.libraries.rxnearby.SERVICE_ID
import rpimusicbox.libraries.rxnearby.discovery.exceptions.AlreadyDiscoveringEndpointsException
import rpimusicbox.libraries.rxnearby.discovery.exceptions.MissingAccessCoarseLocationPermissionException
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer

private const val MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE = 8034

internal class DiscoveryEventObservable(private val connectionsClient: ConnectionsClient) : Observable<DiscoveryEvent>() {

    override fun subscribeActual(observer: Observer<in DiscoveryEvent>) {
        observer.onSubscribe(object : SimpleDisposable() {
            override fun onDispose() {
                connectionsClient.stopDiscovery()
            }
        })

        observer.onNext(DiscoveryInitiated)

        connectionsClient.startDiscovery(SERVICE_ID, EndpointDiscoveryListener(observer), DiscoveryOptions(Strategy.P2P_STAR))
                .addOnSuccessListener { observer.onNext(DiscoveryStarted) }
                .addOnFailureListener {
                    if (it is ApiException && it.statusCode == MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE) {
                        observer.onError(MissingAccessCoarseLocationPermissionException)
                    } else {
                        observer.onError(AlreadyDiscoveringEndpointsException)
                    }
                }
    }
}


private class EndpointDiscoveryListener(private val observer: Observer<in DiscoveryEvent>) : EndpointDiscoveryCallback() {

    override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        observer.onNext(EndpointFound(Endpoint(endpointId, discoveredEndpointInfo.endpointName, discoveredEndpointInfo.serviceId)))
    }

    override fun onEndpointLost(lostEndpointId: String) {
        observer.onNext(EndpointLost(lostEndpointId))
    }
}