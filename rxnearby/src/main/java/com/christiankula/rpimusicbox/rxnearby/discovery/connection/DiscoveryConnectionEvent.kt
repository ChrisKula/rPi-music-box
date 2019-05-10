package com.christiankula.rpimusicbox.rxnearby.discovery.connection

import com.christiankula.rpimusicbox.rxnearby.Endpoint

sealed class DiscoveryConnectionEvent

data class ConnectionRequested(val endpoint: Endpoint) : DiscoveryConnectionEvent()
data class ConnectionInitiated(val endpoint: Endpoint) : DiscoveryConnectionEvent()
data class ConnectionApproved(val endpointId: String) : DiscoveryConnectionEvent()
data class ConnectionRejected(val endpointId: String) : DiscoveryConnectionEvent()
data class ConnectionError(val endpointId: String) : DiscoveryConnectionEvent()

data class EndpointDisconnected(val endpointId: String) : DiscoveryConnectionEvent()

data class PayloadReceived(val endpointId: String, val payload: String) : DiscoveryConnectionEvent()
