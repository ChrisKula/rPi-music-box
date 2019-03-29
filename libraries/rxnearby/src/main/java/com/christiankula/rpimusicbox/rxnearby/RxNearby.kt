package com.christiankula.rpimusicbox.rxnearby

import android.Manifest
import android.content.Context
import com.christiankula.rpimusicbox.rxnearby.advertise.AdvertisingEvent
import com.christiankula.rpimusicbox.rxnearby.advertise.AdvertisingObservable
import com.christiankula.rpimusicbox.rxnearby.connection.ConnectionLifecycleObservable
import com.christiankula.rpimusicbox.rxnearby.connection.ConnectionStatus
import com.christiankula.rpimusicbox.rxnearby.discovery.DiscoveryEvent
import com.christiankula.rpimusicbox.rxnearby.discovery.DiscoveryEventObservable
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

    fun requestConnection(endpointId: String, clientName: String): Observable<ConnectionStatus> {
        return ConnectionLifecycleObservable(connectionsClient, endpointId, clientName).subscribeOn(Schedulers.io())
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
