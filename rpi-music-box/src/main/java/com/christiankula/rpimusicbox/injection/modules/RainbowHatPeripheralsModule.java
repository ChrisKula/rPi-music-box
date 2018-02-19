package com.christiankula.rpimusicbox.injection.modules;


import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RainbowHatPeripheralsModule {

    @Singleton
    @Provides
    Speaker provideSpeaker() {
        try {
            return RainbowHat.openPiezo();
        } catch (IOException e) {
            throw new RuntimeException("Unable to open piezo speaker");
        }
    }

    @Singleton
    @Provides
    AlphanumericDisplay provideAlphanumericDisplay() {
        try {
            AlphanumericDisplay display = RainbowHat.openDisplay();

            display.setEnabled(true);

            return display;
        } catch (IOException e) {
            throw new RuntimeException("Unable to open alphanumeric display");
        }
    }

}
