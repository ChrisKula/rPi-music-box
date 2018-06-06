package com.christiankula.rpimusicbox.things.injection.components;


import com.christiankula.rpimusicbox.things.injection.modules.ApplicationModule;
import com.christiankula.rpimusicbox.things.injection.modules.HomeModule;
import com.christiankula.rpimusicbox.things.injection.modules.NearbyModule;
import com.christiankula.rpimusicbox.things.injection.modules.RainbowHatPeripheralsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, HomeModule.class, RainbowHatPeripheralsModule.class, NearbyModule.class})
public interface RPiMusicBoxComponent extends ApplicationComponent {

}
