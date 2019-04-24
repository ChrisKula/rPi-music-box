package com.christiankula.rpimusicbox.rxnearby.discovery.exceptions

/**
 * Exception thrown when starting discovery whereas the [ConnectionsClient] is already discovering
 */
internal object AlreadyDiscoveringEndpointsException : Exception("Client already discovering")
