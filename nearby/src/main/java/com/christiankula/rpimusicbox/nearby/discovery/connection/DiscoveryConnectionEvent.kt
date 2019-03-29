package com.christiankula.rpimusicbox.nearby.discovery.connection

import com.christiankula.rpimusicbox.nearby.Endpoint

sealed class DiscoveryConnectionEvent

data class ConnectionInitiated(val endpoint: Endpoint) : DiscoveryConnectionEvent()
data class ConnectionApproved(val endpointId: String) : DiscoveryConnectionEvent()
data class ConnectionRejected(val endpointId: String) : DiscoveryConnectionEvent()
data class ConnectionError(val endpointId: String) : DiscoveryConnectionEvent()

data class EndpointDisconnected(val endpointId: String) : DiscoveryConnectionEvent()

data class PayloadReceived(val endpointId: String, val payload: String) : DiscoveryConnectionEvent()
