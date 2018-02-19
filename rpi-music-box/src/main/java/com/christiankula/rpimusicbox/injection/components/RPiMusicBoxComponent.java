package com.christiankula.rpimusicbox.injection.components;


import com.christiankula.rpimusicbox.injection.modules.RainbowHatPeripheralsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RainbowHatPeripheralsModule.class})
public interface RPiMusicBoxComponent extends ApplicationComponent {

}
