package com.christiankula.rpimusicbox.home;

import android.support.annotation.NonNull;

import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

public class HomePresenter implements HomeMvp.Presenter, OnSuccessListener<Void>, OnFailureListener {

    private HomeMvp.View view;

    private HomeMvp.Model model;

    private final ConnectionLifecycleCallback cb = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
            //Accept everyone
            model.acceptConnection(endpointId, payloadCallback);
        }

        @Override
        public void onConnectionResult(String endpointId, ConnectionResolution connectionResolution) {
            switch (connectionResolution.getStatus().getStatusCode()) {
                case ConnectionsStatusCodes.STATUS_OK:
                    // We're connected! We can now start sending and receiving data.
                    break;
                case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                    // The connection was rejected by one or both sides.
                    break;
                case ConnectionsStatusCodes.STATUS_ERROR:
                    // The connection broke before it was able to be accepted.
                    break;
            }
        }

        @Override
        public void onDisconnected(String endpointId) {
            //TODO Say bye bye to the endpoint
        }
    };

    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(String endpointId, Payload payload) {
            //TODO Define what kind of Payload will be sent
        }

        @Override
        public void onPayloadTransferUpdate(String s, PayloadTransferUpdate payloadTransferUpdate) {
            //NOT USED
        }
    };

    @Inject
    public HomePresenter(HomeMvp.Model model) {
        this.model = model;
    }

    @Override
    public void onViewAttached(HomeMvp.View view) {
        this.view = view;

        this.model.startAdvertising(cb, this, this);
    }

    @Override
    public void onViewDetached() {
        this.model.stopAdvertising();

        this.view = null;
    }

    @Override
    public void onSuccess(Void aVoid) {
        this.view.displayAdvertisingSuccess();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.view.displayAdvertisingFailure();
        e.printStackTrace();
    }
}
