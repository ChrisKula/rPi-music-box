package com.christiankula.rpimusicbox.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.christiankula.rpimusicbox.RPiMusicBoxApplication;
import com.christiankula.rpimusicbox.rainbowhat.RainbowHatHelper;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeMvp.View {

    @Inject
    RainbowHatHelper rainbowHatHelper;

    private HomeMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RPiMusicBoxApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.presenter.onViewAttached(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.presenter.onViewDetached();
    }

    @Inject
    @Override
    public void setPresenter(HomeMvp.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAdvertisingOngoing() {
        rainbowHatHelper.setBlueLedValue(true);
        rainbowHatHelper.display("Initialisation");
    }

    @Override
    public void showAdvertisingSuccess() {
        rainbowHatHelper.setGreenLedValue(true);
        rainbowHatHelper.display("Discovering ...");
    }

    @Override
    public void sayHello(String endpointName) {
        rainbowHatHelper.display("Hello " + endpointName, 5000);
    }

    @Override
    public void sayGoodBye(String endpointName) {
        rainbowHatHelper.display("Goodbye " + endpointName, 5000);
    }

    @Override
    public void showAdvertisingFailure() {
        rainbowHatHelper.setRedLedValue(true);
    }
}
