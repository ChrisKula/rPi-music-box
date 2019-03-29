package com.christiankula.rpimusicbox.nearby.discovery

import com.christiankula.rpimusicbox.nearby.Endpoint
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import io.reactivex.Observer

internal class DiscoveryCallback(private val observer: Observer<in DiscoveryEvent>) : EndpointDiscoveryCallback() {

    override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        observer.onNext(EndpointFound(Endpoint(endpointId, discoveredEndpointInfo.endpointName, discoveredEndpointInfo.serviceId)))
    }

    override fun onEndpointLost(lostEndpointId: String) {
        observer.onNext(EndpointLost(lostEndpointId))
    }
}
