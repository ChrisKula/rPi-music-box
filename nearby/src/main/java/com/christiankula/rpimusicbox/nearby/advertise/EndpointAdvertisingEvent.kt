package com.christiankula.rpimusicbox.nearby.advertise

import com.christiankula.rpimusicbox.nearby.Endpoint

sealed class EndpointAdvertisingEvent

object EndpointAdvertisingStarted : EndpointAdvertisingEvent()
data class EndpointConnectionInitiated(val endpoint: Endpoint) : EndpointAdvertisingEvent()
data class EndpointConnected(val endpointId: String) : EndpointAdvertisingEvent()
data class EndpointConnectionRejected(val endpointId: String) : EndpointAdvertisingEvent()
data class EndpointConnectionError(val endpointId: String) : EndpointAdvertisingEvent()
data class EndpointDisconnected(val endpointId: String) : EndpointAdvertisingEvent()
