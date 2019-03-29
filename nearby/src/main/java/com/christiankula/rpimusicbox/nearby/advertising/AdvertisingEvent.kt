package com.christiankula.rpimusicbox.nearby.advertising

import com.christiankula.rpimusicbox.nearby.Endpoint

sealed class AdvertisingEvent

data class AdvertisingStarted(val serverName: String) : AdvertisingEvent()

data class ConnectionInitiated(val endpoint: Endpoint) : AdvertisingEvent()
data class ConnectionApproved(val endpointId: String) : AdvertisingEvent()
data class ConnectionRejected(val endpointId: String) : AdvertisingEvent()
data class ConnectionError(val endpointId: String) : AdvertisingEvent()

data class EndpointDisconnected(val endpointId: String) : AdvertisingEvent()

data class PayloadReceived(val endpointId: String, val payload: String) : AdvertisingEvent()