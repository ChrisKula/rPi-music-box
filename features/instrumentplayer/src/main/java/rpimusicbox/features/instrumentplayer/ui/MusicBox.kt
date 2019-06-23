package rpimusicbox.features.instrumentplayer.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import rpimusicbox.libraries.rxnearby.Endpoint

/**
 * Data class representing a music box a client can interact with
 */
@Parcelize
data class MusicBox(val id: String, val name: String, val serviceId: String) : Parcelable

fun fromEndpoint(endpoint: Endpoint): MusicBox {
    return MusicBox(endpoint.id, endpoint.name, endpoint.serviceId)
}
