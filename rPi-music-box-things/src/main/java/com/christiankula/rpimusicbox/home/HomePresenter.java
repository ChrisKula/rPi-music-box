package com.christiankula.rpimusicbox.home;

import android.support.annotation.NonNull;

import com.christiankula.rpimusicbox.nearby.connection.Endpoint;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

//TODO Rxify evertying
public class HomePresenter implements HomeMvp.Presenter, OnCompleteListener<Void> {

    private HomeMvp.View view;

    private HomeMvp.Model model;

    private final ConnectionLifecycleCallback cb = new ConnectionLifecycleCallback() {
        @Override
        public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
            //Accept everyone
            model.acceptConnection(endpointId, payloadCallback);

            model.addEndpoint(endpointId, connectionInfo.getEndpointName());
        }

        @Override
        public void onConnectionResult(String endpointId, ConnectionResolution connectionResolution) {
            switch (connectionResolution.getStatus().getStatusCode()) {
                case ConnectionsStatusCodes.STATUS_OK: {
                    // We're connected! We can now start sending and receiving data.
                    model.setEndpointAsConnected(endpointId);

                    Endpoint endpoint = model.getEndpoint(endpointId);

                    if (endpoint != null) {
                        view.sayHello(endpoint.getEndpointName());
                    }
                }
                break;
                case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                case ConnectionsStatusCodes.STATUS_ERROR:
                    removeEndpoint(endpointId);
                    break;
            }
        }

        @Override
        public void onDisconnected(String endpointId) {
            // We've been disconnected from this endpoint. No more data can be sent or received.
            removeEndpoint(endpointId);
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

        this.view.showAdvertisingOngoing();

        this.model.startAdvertising(cb, this);
    }

    @Override
    public void onViewDetached() {
        this.model.stopAdvertising();

        this.view = null;
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            this.view.showAdvertisingSuccess();
        } else {
            this.view.showAdvertisingFailure();
            if (task.getException() != null) {
                task.getException().printStackTrace();
            }
        }
    }

    private void removeEndpoint(String endpointId) {
        Endpoint endpoint = this.model.removeEndpoint(endpointId);

        if (endpoint != null) {
            this.view.sayGoodBye(endpoint.getEndpointName());
        }
    }
}
