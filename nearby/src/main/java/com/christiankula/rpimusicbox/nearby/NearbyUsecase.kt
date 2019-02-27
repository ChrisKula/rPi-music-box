package com.christiankula.rpimusicbox.nearby

import android.Manifest
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryEvent
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryEventObservable
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.AlreadyDiscoveringEndpointsException
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.MissingAccessCoarseLocationPermissionException
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

internal const val SERVICE_ID = "RPI_MUSIC_BOX"

class NearbyUsecase(private val connectionsClient: ConnectionsClient) {

    /**
     * Start discovering endpoints and return an Observable of [EndpointDiscoveryEvent].
     *
     * **Warning** : Only one Observable can be used at a time. If there's already a discovering ongoing,
     * the Observable will terminate with an [AlreadyDiscoveringEndpointsException].
     *
     * Also, this call needs [Manifest.permission.ACCESS_COARSE_LOCATION], the Observable will terminate
     * with an [MissingAccessCoarseLocationPermissionException] if the permission hasn't been granted beforehand.
     */
    fun observeEndpointDiscovery(): Observable<EndpointDiscoveryEvent> {
        return EndpointDiscoveryEventObservable(connectionsClient)
                .subscribeOn(Schedulers.io())
    }

    fun requestConnection(endpointId: String): Observable<Pair<String, Payload>> {
        return EndpointPayloadObservable(endpointId, connectionsClient).subscribeOn(Schedulers.io())
    }
}
