package com.christiankula.rpimusicbox.nearby.discovery.exceptions

/**
 * Exception thrown when starting discovery whereas the [ConnectionsClient] is already discovering
 */
internal object AlreadyDiscoveringEndpointsException : Exception("Client already discovering")
