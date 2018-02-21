package com.christiankula.rpimusicbox.home;


import com.christiankula.rpimusicbox.mvp.BasePresenter;
import com.christiankula.rpimusicbox.mvp.BaseView;
import com.christiankula.rpimusicbox.nearby.connection.Endpoint;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnCompleteListener;

public interface HomeMvp {

    interface Model {

        void startAdvertising(ConnectionLifecycleCallback clcb, OnCompleteListener<Void> onCompleteListener);

        void stopAdvertising();

        void acceptConnection(String endpointId, PayloadCallback payloadCallback);

        void addEndpoint(String endpointId, String endpointName);

        void setEndpointAsConnected(String endpointId);

        Endpoint removeEndpoint(String endpointId);

        Endpoint getEndpoint(String endpointId);
    }

    interface View extends BaseView<Presenter> {

        void showAdvertisingOngoing();

        void showAdvertisingSuccess();

        void showAdvertisingFailure();

        void sayHello(String endpointName);

        void sayGoodBye(String endpointName);
    }

    interface Presenter extends BasePresenter<View> {

    }

}
