package com.christiankula.rpimusicbox.home;

import com.christiankula.rpimusicbox.BuildConfig;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnCompleteListener;

import javax.inject.Inject;

public class HomeModel implements HomeMvp.Model {

    private ConnectionsClient connectionsClient;
    private AdvertisingOptions advertisingOptions;

    @Inject
    public HomeModel(ConnectionsClient connectionsClient, AdvertisingOptions advertisingOptions) {
        this.connectionsClient = connectionsClient;
        this.advertisingOptions = advertisingOptions;
    }

    @Override
    public void startAdvertising(ConnectionLifecycleCallback clcb, OnCompleteListener<Void> onCompleteListener) {
        //TODO Replace string & BuildConfig.APPLICATION_ID
        this.connectionsClient.startAdvertising("rPi Music Box",
                BuildConfig.APPLICATION_ID,
                clcb,
                advertisingOptions)
                .addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void stopAdvertising() {
        this.connectionsClient.stopAdvertising();
    }

    @Override
    public void acceptConnection(String endpointId, PayloadCallback payloadCallback) {
        this.connectionsClient.acceptConnection(endpointId, payloadCallback);
    }
}
