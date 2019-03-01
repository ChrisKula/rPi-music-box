package com.christiankula.rpimusicbox.nearby.discovery

import com.christiankula.rpimusicbox.nearby.Endpoint

sealed class EndpointDiscoveryEvent

/**
 * An endpoint has been found
 *
 * @param endpoint the found endpoint
 */
data class EndpointFound(val endpoint: Endpoint) : EndpointDiscoveryEvent()

/**
 * A previously found endpoint has been lost
 *
 * @param id the ID of the lost endpoint
 */
data class EndpointLost(val id: String) : EndpointDiscoveryEvent()

/**
 * The endpoint discovery has been initiated but the device is not yet searching
 */
object EndpointDiscoveryInitiated : EndpointDiscoveryEvent()

/**
 * The endpoint discovery has been correctly started and the devices is searching for endpoints
 */
object EndpointDiscoveryStarted : EndpointDiscoveryEvent()
