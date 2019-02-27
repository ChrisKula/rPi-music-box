package com.christiankula.rpimusicbox.nearby

import android.Manifest
import com.christiankula.rpimusicbox.commons.rxjava2.SimpleDisposable
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers

private const val SERVICE_ID = "RPI_MUSIC_BOX"

private const val MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE = 8034

class NearbyUsecase(private val connectionsClient: ConnectionsClient) {

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

    fun requestConnection(endpointId: String): Observable<Pair<String, Payload>> {
        return EndpointPayloadObservable(endpointId, connectionsClient).subscribeOn(Schedulers.io())
    }
}

private class EndpointDiscoveryEventObservable(private val connectionsClient: ConnectionsClient) : Observable<EndpointDiscoveryEvent>() {

    override fun subscribeActual(observer: Observer<in EndpointDiscoveryEvent>) {
        observer.onSubscribe(object : SimpleDisposable() {
            override fun onDispose() {
                connectionsClient.stopDiscovery()
            }
        })

        observer.onNext(EndpointDiscoveryInitiated)

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

private class EndpointPayloadObservable(private val endpointId: String, private val connectionsClient: ConnectionsClient) : Observable<Pair<String, Payload>>() {
    override fun subscribeActual(observer: Observer<in Pair<String, Payload>>) {
        connectionsClient.acceptConnection(endpointId, object : PayloadCallback() {
            override fun onPayloadReceived(endpointId: String, payload: Payload) {
                observer.onNext(endpointId to payload)
            }

            override fun onPayloadTransferUpdate(endpointId: String, payloadTransferUpdate: PayloadTransferUpdate) {
                // Not used
            }
        }).addOnFailureListener {
            observer.onError(it)
        }
    }
}

sealed class EndpointDiscoveryEvent

/**
 * An endpoint has been found
 *
 * @param endpoint the found endpoint
 */
data class EndpointFound(val endpoint: Endpoint) : EndpointDiscoveryEvent()

/**
 * A previously found endpoint has been lost
 *
 * @param id the ID of the lost endpoint
 */
data class EndPointLost(val id: String) : EndpointDiscoveryEvent()

/**
 * The endpoint discovery has been initiated but the device is not yet searching
 */
object EndpointDiscoveryInitiated : EndpointDiscoveryEvent()

/**
 * The endpoint discovery has been correctly started and the devices is searching for endpoints
 */
object EndpointDiscoveryStarted : EndpointDiscoveryEvent()

/**
 * Exception thrown when starting discovery whereas the [ConnectionsClient] is already discovering
 */
object AlreadyDiscoveringEndpointsException : Exception("Client already discovering")

object MissingAccessCoarseLocationPermissionException : Exception("Missing access to coarse location permission")
