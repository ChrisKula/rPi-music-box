package com.christiankula.rpimusicbox.things.injection.modules;


import com.christiankula.rpimusicbox.things.rainbowhat.RainbowHatHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RainbowHatPeripheralsModule {

    @Singleton
    @Provides
    RainbowHatHelper provideRainbowHatHelper() {
        return new RainbowHatHelper();
    }
}
