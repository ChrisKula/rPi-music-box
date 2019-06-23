package rpimusicbox.libraries.rxnearby.advertise

import rpimusicbox.libraries.rxnearby.Endpoint
import rpimusicbox.libraries.rxnearby.SERVICE_ID
import com.google.android.gms.nearby.connection.*
import io.reactivex.Observer


internal class AdvertisingConnectionLifecycleCallback(private val observer: Observer<in AdvertisingEvent>,
                                                      private val connectionsClient: ConnectionsClient)
    : ConnectionLifecycleCallback() {

    override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
        connectionsClient.acceptConnection(endpointId, AdvertisingPayloadCallback(observer))
        observer.onNext(ConnectionInitiated(Endpoint(endpointId, connectionInfo.endpointName, SERVICE_ID)))
    }

    override fun onConnectionResult(endpointId: String, connectionResolution: ConnectionResolution) {
        when (connectionResolution.status.statusCode) {
            ConnectionsStatusCodes.STATUS_OK -> observer.onNext(ConnectionApproved(endpointId))
            ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> observer.onNext(ConnectionRejected(endpointId))
            ConnectionsStatusCodes.STATUS_ERROR -> observer.onNext(ConnectionError(endpointId))
        }
    }

    override fun onDisconnected(endpointId: String) {
        observer.onNext(EndpointDisconnected(endpointId))
    }
}
