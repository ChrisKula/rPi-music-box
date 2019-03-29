package com.christiankula.rpimusicbox.nearby.advertising

import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import io.reactivex.Observer

internal class AdvertisingPayloadCallback(private val observer: Observer<in AdvertisingEvent>) : PayloadCallback() {
    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        //TODO Change 'payload.toString()'
        observer.onNext(PayloadReceived(endpointId, payload.toString()))
    }

    override fun onPayloadTransferUpdate(endpointId: String, payloadTransferUpdate: PayloadTransferUpdate) {
        // Not used
    }
}
