package com.christiankula.rpimusicbox.nearby.discovery

import com.christiankula.rpimusicbox.commons.rxjava2.SimpleDisposable
import com.christiankula.rpimusicbox.nearby.Endpoint
import com.christiankula.rpimusicbox.nearby.SERVICE_ID
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.AlreadyDiscoveringEndpointsException
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.MissingAccessCoarseLocationPermissionException
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer

private const val MISSING_PERMISSION_ACCESS_COARSE_LOCATION_STATUS_CODE = 8034

internal class EndpointDiscoveryEventObservable(private val connectionsClient: ConnectionsClient) : Observable<EndpointDiscoveryEvent>() {

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
        observer.onNext(EndpointLost(lostEndpointId))
    }
}
