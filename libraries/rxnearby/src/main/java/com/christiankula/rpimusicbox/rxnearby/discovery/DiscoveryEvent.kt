package com.christiankula.rpimusicbox.rxnearby.discovery

import com.christiankula.rpimusicbox.rxnearby.Endpoint

sealed class DiscoveryEvent

/**
 * An endpoint has been found
 *
 * @param endpoint the found endpoint
 */
data class EndpointFound(val endpoint: Endpoint) : DiscoveryEvent()

/**
 * A previously found endpoint has been lost
 *
 * @param id the ID of the lost endpoint
 */
data class EndpointLost(val id: String) : DiscoveryEvent()

/**
 * The endpoint discovery has been initiated but the device is not yet searching
 */
object DiscoveryInitiated : DiscoveryEvent()

/**
 * The endpoint discovery has been correctly started and the devices is searching for endpoints
 */
object DiscoveryStarted : DiscoveryEvent()
