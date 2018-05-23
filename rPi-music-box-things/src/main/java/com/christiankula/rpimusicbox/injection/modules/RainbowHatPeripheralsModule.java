package com.christiankula.rpimusicbox.injection.modules;


import com.christiankula.rpimusicbox.rainbowhat.RainbowHatHelper;

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
