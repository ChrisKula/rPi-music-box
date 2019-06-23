package rpimusicbox.features.discovery.models

import android.os.Parcelable
import com.christiankula.rpimusicbox.rxnearby.Endpoint
import kotlinx.android.parcel.Parcelize

/**
 * Data class representing a music box a client can interact with
 */
@Parcelize
data class MusicBox(val id: String, val name: String, val serviceId: String) : Parcelable

fun fromEndpoint(endpoint: Endpoint): MusicBox {
    return MusicBox(endpoint.id, endpoint.name, endpoint.serviceId)
}
