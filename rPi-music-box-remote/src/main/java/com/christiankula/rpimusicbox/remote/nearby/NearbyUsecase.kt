package com.christiankula.rpimusicbox.remote.nearby

import android.Manifest
import com.christiankula.rpimusicbox.remote.rxjava2.SimpleDisposable
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO Move this const to a common module
const val SERVICE_ID = "RPI_MUSIC_BOX"

private const val MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE = 8034

class NearbyUsecase @Inject constructor(private val connectionsClient: ConnectionsClient) {

    /**
     * Start discovering endpoints and return an Observable of [EndpointDiscoveryEvent].
     *
     * **Warning** : Only one Observable can be used at a time. If there's already a discovering ongoing,
     * the Observable will terminate with an [AlreadyDiscoveringEndpointsException].
     *
     * Also, this call needs [Manifest.permission.ACCESS_COARSE_LOCATION], the Observable will terminate
     * with an [MissingAccessCoarseLocationPermissionException] if the permission hasn't been granted beforehand.
     */
    fun observeEndpointDiscovery(): Observable<EndpointDiscoveryEvent> {
        return EndpointDiscoveryEventObservable(connectionsClient)
                .subscribeOn(Schedulers.io())
    }
}

private class EndpointDiscoveryEventObservable(private val connectionsClient: ConnectionsClient) : Observable<EndpointDiscoveryEvent>() {

    override fun subscribeActual(observer: Observer<in EndpointDiscoveryEvent>) {
        observer.onSubscribe(object : SimpleDisposable() {
            override fun onDispose() {
                connectionsClient.stopDiscovery()
            }
        })

        connectionsClient.startDiscovery(SERVICE_ID, EndpointDiscoveryListener(observer), DiscoveryOptions(Strategy.P2P_STAR))
                .addOnSuccessListener { observer.onNext(EndpointDiscoveryStarted) }
                .addOnFailureListener {
                    if (it is ApiException && it.statusCode == MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE) {
                        observer.onError(MissingAccessCoarseLocationPermissionException)
                    } else {
                        observer.onError(AlreadyDiscoveringEndpointsException)
                    }
                }
    }
}

private class EndpointDiscoveryListener(private val observer: Observer<in EndpointDiscoveryEvent>) : EndpointDiscoveryCallback() {

    override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        observer.onNext(EndpointFound(Endpoint(endpointId, discoveredEndpointInfo.endpointName, discoveredEndpointInfo.serviceId)))
    }

    override fun onEndpointLost(lostEndpointId: String) {
        observer.onNext(EndPointLost(lostEndpointId))
    }
}

sealed class EndpointDiscoveryEvent

data class EndpointFound(val endpoint: Endpoint) : EndpointDiscoveryEvent()
data class EndPointLost(val id: String) : EndpointDiscoveryEvent()

object EndpointDiscoveryStarted : EndpointDiscoveryEvent()

/**
 * Exception thrown when starting discovery whereas the [ConnectionsClient] is already discovering
 */
object AlreadyDiscoveringEndpointsException : Exception("Client already discovering")

object MissingAccessCoarseLocationPermissionException : Exception("")
