package com.christiankula.rpimusicbox.rxnearby.connection

import com.google.android.gms.nearby.connection.*
import io.reactivex.Observable
import io.reactivex.Observer

internal class ConnectionLifecycleObservable(private val connectionsClient: ConnectionsClient,
                                             private val endpointId: String,
                                             private val clientName: String) : Observable<ConnectionStatus>() {

    override fun subscribeActual(observer: Observer<in ConnectionStatus>) {
        connectionsClient.requestConnection(clientName, endpointId, ConnectionStatusListener(observer))
    }
}

private class ConnectionStatusListener(private val observer: Observer<in ConnectionStatus>) : ConnectionLifecycleCallback() {

    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        observer.onNext(ConnectionInitiated)
    }

    override fun onConnectionResult(endpointId: String, connectionResolution: ConnectionResolution) {
        when (connectionResolution.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> {
                observer.onNext(ConnectionApproved)
                observer.onComplete()
            }

            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                observer.onNext(ConnectionRejected)
                observer.onComplete()
            }

            ConnectionsStatusCodes.STATUS_ERROR -> {
                observer.onNext(ConnectionError)
                observer.onComplete()
            }
        }
    }

    override fun onDisconnected(endpointId: String) {
        observer.onNext(DisconnectedFromEndpoint(endpointId))
    }
}
