package rpimusicbox.libraries.rxnearby

import android.Manifest
import android.content.Context
import rpimusicbox.libraries.rxnearby.advertise.AdvertisingEvent
import rpimusicbox.libraries.rxnearby.advertise.AdvertisingObservable
import rpimusicbox.libraries.rxnearby.discovery.DiscoveryEvent
import rpimusicbox.libraries.rxnearby.discovery.DiscoveryEventObservable
import rpimusicbox.libraries.rxnearby.discovery.connection.DiscoveryConnectionEvent
import rpimusicbox.libraries.rxnearby.discovery.connection.DiscoveryConnectionObservable
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal const val SERVICE_ID = "RPI_MUSIC_BOX"

class RxNearby(context: Context) {

    private val connectionsClient: ConnectionsClient = Nearby.getConnectionsClient(context)

    /**
     * Start discovering endpoints and return an Observable of [DiscoveryEvent].
     *
     * **Warning** : Only one Observable can be used at a time. If there's already a discovering ongoing,
     * the Observable will terminate with an [AlreadyDiscoveringEndpointsException].
     *
     * Also, this call needs [Manifest.permission.ACCESS_COARSE_LOCATION], the Observable will terminate
     * with an [MissingAccessCoarseLocationPermissionException] if the permission hasn't been granted beforehand.
     */
    fun observeDiscovery(): Observable<DiscoveryEvent> {
        return DiscoveryEventObservable(connectionsClient).subscribeOn(Schedulers.io())
    }

    /**
     * Start a connection with an advertiser and return an Observable of [DiscoveryConnectionEvent].
     *
     * It will try to connect to the advertiser with the given advertiser ID and the stream will emits
     * connection status events. When it has connected successfully, it will also start emitting payloads from
     * the advertiser.
     *
     * @param advertiserId the ID of the advertiser to connect to
     * @param clientName the name of the client that will connect to the advertiser
     *
     */
    fun observeDiscoveryConnection(advertiserId: String, clientName: String): Observable<DiscoveryConnectionEvent> {
        return DiscoveryConnectionObservable(advertiserId, clientName, connectionsClient).subscribeOn(Schedulers.io())
    }

    /**
     * Start advertising and return an Observable of [AdvertisingEvent]
     *
     * This stream will emit events from external clients that are discovering and that will
     * try to connect to this advertiser. This advertiser will accept all incoming connections and will
     * emit payloads coming from all external clients.
     *
     * @param advertiserName the name of this advertiser that will be shown to discovering endpoints
     */
    fun observeAdvertising(advertiserName: String): Observable<AdvertisingEvent> {
        return AdvertisingObservable(advertiserName, connectionsClient).subscribeOn(Schedulers.io())
    }
}
