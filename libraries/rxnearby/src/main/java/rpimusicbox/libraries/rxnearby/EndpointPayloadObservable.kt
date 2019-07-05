package rpimusicbox.libraries.rxnearby

import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import io.reactivex.Observable
import io.reactivex.Observer

internal class EndpointPayloadObservable(private val endpointId: String, private val connectionsClient: ConnectionsClient) : Observable<Pair<String, Payload>>() {
    override fun subscribeActual(observer: Observer<in Pair<String, Payload>>) {
        connectionsClient.acceptConnection(endpointId, object : PayloadCallback() {
            override fun onPayloadReceived(endpointId: String, payload: Payload) {
                observer.onNext(endpointId to payload)
            }

            override fun onPayloadTransferUpdate(endpointId: String, payloadTransferUpdate: PayloadTransferUpdate) {
                // Not used
            }
        }).addOnFailureListener {
            observer.onError(it)
        }
    }
}
