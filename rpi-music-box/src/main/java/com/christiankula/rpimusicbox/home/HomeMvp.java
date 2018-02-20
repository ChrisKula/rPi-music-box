package com.christiankula.rpimusicbox.home;


import com.christiankula.rpimusicbox.mvp.BasePresenter;
import com.christiankula.rpimusicbox.mvp.BaseView;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.tasks.OnCompleteListener;

public interface HomeMvp {

    interface Model {

        void startAdvertising(ConnectionLifecycleCallback clcb, OnCompleteListener<Void> onCompleteListener);

        void stopAdvertising();

        void acceptConnection(String endpointId, PayloadCallback payloadCallback);
    }

    interface View extends BaseView<Presenter> {

        void showAdvertisingOngoing();

        void showAdvertisingSuccess();

        void showAdvertisingFailure();
    }

    interface Presenter extends BasePresenter<View> {

    }

}
