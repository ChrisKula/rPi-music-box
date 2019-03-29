package com.christiankula.rpimusicbox.nearby.discovery

import com.christiankula.rpimusicbox.commons.rxjava2.SimpleDisposable
import com.christiankula.rpimusicbox.nearby.SERVICE_ID
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.AlreadyDiscoveringEndpointsException
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.MissingAccessCoarseLocationPermissionException
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.Strategy
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

        connectionsClient.startDiscovery(SERVICE_ID, DiscoveryCallback(observer), DiscoveryOptions(Strategy.P2P_STAR))
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
