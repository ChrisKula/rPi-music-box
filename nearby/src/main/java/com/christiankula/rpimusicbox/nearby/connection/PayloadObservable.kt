package com.christiankula.rpimusicbox.nearby.connection

import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import io.reactivex.Observable
import io.reactivex.Observer

internal class PayloadObservable(private val connectionsClient: ConnectionsClient,
                                 private val endpointId: String) : Observable<String>() {

    override fun subscribeActual(observer: Observer<in String>) {
        connectionsClient.acceptConnection(endpointId, object : PayloadCallback() {
            override fun onPayloadReceived(endpointId: String, payload: Payload) {
                TODO("Implement onPayloadReceived")
            }

            override fun onPayloadTransferUpdate(endpointId: String, payloadTransferUpdate: PayloadTransferUpdate) {
                // Not used as no files or streams are sent
            }
        })
    }
}
