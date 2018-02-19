package com.christiankula.rpimusicbox.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.christiankula.rpimusicbox.RPiMusicBoxApplication;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;

import java.io.IOException;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    AlphanumericDisplay display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RPiMusicBoxApplication) getApplication()).getComponent().inject(this);

        try {
            display.display("Create");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
