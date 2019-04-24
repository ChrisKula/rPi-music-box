package com.christiankula.rpimusicbox.rxnearby.advertise

import com.christiankula.rpimusicbox.commons.rxjava2.SimpleDisposable
import com.christiankula.rpimusicbox.rxnearby.Endpoint
import com.christiankula.rpimusicbox.rxnearby.SERVICE_ID
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer

internal class EndpointAdvertisingEventObservable(private val connectionsClient: ConnectionsClient,
                                                  private val serverName: String) : Observable<EndpointAdvertisingEvent>() {
    override fun subscribeActual(observer: Observer<in EndpointAdvertisingEvent>) {
        observer.onSubscribe(object : SimpleDisposable() {
            override fun onDispose() {
                connectionsClient.stopAdvertising()
            }
        })

        connectionsClient.startAdvertising(serverName, SERVICE_ID, EndpointAdvertisingListener(observer), AdvertisingOptions(Strategy.P2P_STAR))
                .addOnSuccessListener { observer.onNext(EndpointAdvertisingStarted) }
                .addOnFailureListener { observer.onError(it) }
    }
}

private class EndpointAdvertisingListener(private val observer: Observer<in EndpointAdvertisingEvent>) : ConnectionLifecycleCallback() {

    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        observer.onNext(EndpointConnectionInitiated(Endpoint(endpointId, connectionInfo.endpointName, SERVICE_ID)))
    }

    override fun onConnectionResult(endpointId: String, connectionResolution: ConnectionResolution) {
        when (connectionResolution.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> observer.onNext(EndpointConnected(endpointId))
            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> observer.onNext(EndpointConnectionRejected(endpointId))
            ConnectionsStatusCodes.STATUS_ERROR -> observer.onNext(EndpointConnectionError(endpointId))
        }
    }

    override fun onDisconnected(endpointId: String) {
        observer.onNext(EndpointDisconnected(endpointId))
    }
}
