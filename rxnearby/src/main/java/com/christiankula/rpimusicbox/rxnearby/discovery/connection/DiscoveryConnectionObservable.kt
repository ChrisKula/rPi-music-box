package com.christiankula.rpimusicbox.rxnearby.discovery.connection

import com.christiankula.rpimusicbox.rxnearby.Endpoint
import com.christiankula.rpimusicbox.rxnearby.SERVICE_ID
import com.google.android.gms.nearby.connection.ConnectionsClient
import io.reactivex.Observable
import io.reactivex.Observer

internal class DiscoveryConnectionObservable(private val endpointId: String,
                                             private val clientName: String,
                                             private val connectionsClient: ConnectionsClient) : Observable<DiscoveryConnectionEvent>() {

    override fun subscribeActual(observer: Observer<in DiscoveryConnectionEvent>) {
        connectionsClient.requestConnection(clientName, endpointId, DiscoveryConnectionLifecycleCallback(observer, connectionsClient))
        observer.onNext(ConnectionRequested(Endpoint(endpointId, clientName, SERVICE_ID)))
    }
}
