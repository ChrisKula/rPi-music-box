package com.christiankula.rpimusicbox.nearby

import android.Manifest
import com.christiankula.rpimusicbox.nearby.advertise.EndpointAdvertisingEvent
import com.christiankula.rpimusicbox.nearby.advertise.EndpointAdvertisingEventObservable
import com.christiankula.rpimusicbox.nearby.connection.ConnectionLifecycleObservable
import com.christiankula.rpimusicbox.nearby.connection.ConnectionStatus
import com.christiankula.rpimusicbox.nearby.connection.PayloadObservable
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryEvent
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryEventObservable
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.AlreadyDiscoveringEndpointsException
import com.christiankula.rpimusicbox.nearby.discovery.exceptions.MissingAccessCoarseLocationPermissionException
import com.google.android.gms.nearby.connection.ConnectionsClient
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
        return EndpointDiscoveryEventObservable(connectionsClient).subscribeOn(Schedulers.io())
    }

    fun requestConnection(endpointId: String, clientName: String): Observable<ConnectionStatus> {
        return ConnectionLifecycleObservable(connectionsClient, endpointId, clientName).subscribeOn(Schedulers.io())
    }

    // TODO Change the type of Observable (String -> Something else)
    /**
     * Accept the connection to the given endpoint ID. It will start to send payload events down the
     * stream if the connection was successful.
     */
    fun acceptConnection(endpointId: String): Observable<String> {
        return PayloadObservable(connectionsClient, endpointId).subscribeOn(Schedulers.io())
    }

    fun observeAdvertising(serverName: String): Observable<EndpointAdvertisingEvent> {
        return EndpointAdvertisingEventObservable(connectionsClient, serverName).subscribeOn(Schedulers.io())
    }
}
