package rpimusicbox.libraries.rxnearby.advertise

import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Strategy
import io.reactivex.Observable
import io.reactivex.Observer
import rpimusicbox.libraries.commons.rxjava2.SimpleDisposable
import rpimusicbox.libraries.rxnearby.SERVICE_ID

internal class AdvertisingObservable(private val advertiserName: String,
                                     private val connectionsClient: ConnectionsClient) : Observable<AdvertisingEvent>() {

    override fun subscribeActual(observer: Observer<in AdvertisingEvent>) {
        observer.onSubscribe(object : SimpleDisposable() {
            override fun onDispose() {
                connectionsClient.stopAdvertising()
            }
        })

        connectionsClient.startAdvertising(advertiserName, SERVICE_ID,
                AdvertisingConnectionLifecycleCallback(observer, connectionsClient),
                AdvertisingOptions(Strategy.P2P_STAR))
                .addOnSuccessListener { observer.onNext(AdvertisingStarted(advertiserName)) }
                .addOnFailureListener { observer.onError(it) }
    }
}
