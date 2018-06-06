package com.christiankula.rpimusicbox.things.home;

import com.christiankula.rpimusicbox.things.BuildConfig;
import com.christiankula.rpimusicbox.things.nearby.connection.Endpoint;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class HomeModel implements HomeMvp.Model {

    private ConnectionsClient connectionsClient;
    private AdvertisingOptions advertisingOptions;

    private Map<String, Endpoint> foundEndpoints;

    @Inject
    public HomeModel(ConnectionsClient connectionsClient, AdvertisingOptions advertisingOptions) {
        this.connectionsClient = connectionsClient;
        this.advertisingOptions = advertisingOptions;

        this.foundEndpoints = new HashMap<>();
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

    @Override
    public void addEndpoint(String endpointId, String endpointName) {
        foundEndpoints.put(endpointId, new Endpoint(endpointId, endpointName, Endpoint.State.CONNECTION_INITIATED));
    }

    @Override
    public void setEndpointAsConnected(String endpointId) {
        Endpoint endpoint = foundEndpoints.get(endpointId);

        if (endpoint != null) {
            endpoint.setState(Endpoint.State.CONNECTED);
            foundEndpoints.put(endpointId, endpoint);
        }
    }

    @Override
    public Endpoint removeEndpoint(String endpointId) {
        return foundEndpoints.remove(endpointId);
    }

    @Override
    public Endpoint getEndpoint(String endpointId) {
        return foundEndpoints.get(endpointId);
    }
}
