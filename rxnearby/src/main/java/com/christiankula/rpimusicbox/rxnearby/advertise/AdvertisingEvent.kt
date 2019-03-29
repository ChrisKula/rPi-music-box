package com.christiankula.rpimusicbox.rxnearby.advertise

import com.christiankula.rpimusicbox.rxnearby.Endpoint

sealed class AdvertisingEvent

data class AdvertisingStarted(val serverName: String) : AdvertisingEvent()

data class ConnectionInitiated(val endpoint: Endpoint) : AdvertisingEvent()
data class ConnectionApproved(val endpointId: String) : AdvertisingEvent()
data class ConnectionRejected(val endpointId: String) : AdvertisingEvent()
data class ConnectionError(val endpointId: String) : AdvertisingEvent()

data class EndpointDisconnected(val endpointId: String) : AdvertisingEvent()

data class PayloadReceived(val endpointId: String, val payload: String) : AdvertisingEvent()
