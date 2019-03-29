package com.christiankula.rpimusicbox.nearby.discovery.connection

import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import io.reactivex.Observer

internal class DiscoveryConnectionPayloadCallback(private val observer: Observer<in DiscoveryConnectionEvent>) : PayloadCallback() {

    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        observer.onNext(PayloadReceived(endpointId, String(payload.asBytes()!!)))
    }

    override fun onPayloadTransferUpdate(endpointId: String, payloadTransferUpdate: PayloadTransferUpdate) {
        // Not used
    }
}
