package com.christiankula.rpimusicbox.home;


import com.christiankula.rpimusicbox.mvp.BasePresenter;
import com.christiankula.rpimusicbox.mvp.BaseView;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public interface HomeMvp {

    interface Model {

        void startAdvertising(ConnectionLifecycleCallback cb, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);

        void stopAdvertising();

        void acceptConnection(String endpointId, PayloadCallback payloadCallback);
    }

    interface View extends BaseView<Presenter> {

        void displayAdvertisingSuccess();

        void displayAdvertisingFailure();
    }

    interface Presenter extends BasePresenter<View> {

    }

}
