package com.christiankula.rpimusicbox.rxnearby

import com.google.android.gms.nearby.connection.ConnectionsClient

/**
 * Data class representing a Endpoint that can be found while discovering endpoints with a [ConnectionsClient]
 */
data class Endpoint(val id: String, val name: String, val serviceId: String)
