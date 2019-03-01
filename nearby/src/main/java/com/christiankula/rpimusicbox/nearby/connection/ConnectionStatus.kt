package com.christiankula.rpimusicbox.nearby.connection

sealed class ConnectionStatus

object ConnectionInitiated : ConnectionStatus()
object ConnectionApproved : ConnectionStatus()
object ConnectionRejected : ConnectionStatus()

object ConnectionError : ConnectionStatus()

data class DisconnectedFromEndpoint(val endpointId: String) : ConnectionStatus()
