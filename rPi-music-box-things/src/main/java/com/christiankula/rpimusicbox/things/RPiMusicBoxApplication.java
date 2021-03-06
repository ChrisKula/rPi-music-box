package com.christiankula.rpimusicbox.things;

import android.app.Application;

import com.christiankula.rpimusicbox.things.injection.components.DaggerRPiMusicBoxComponent;
import com.christiankula.rpimusicbox.things.injection.components.RPiMusicBoxComponent;
import com.christiankula.rpimusicbox.things.injection.modules.ApplicationModule;
import com.christiankula.rpimusicbox.things.injection.modules.HomeModule;
import com.christiankula.rpimusicbox.things.injection.modules.NearbyModule;
import com.christiankula.rpimusicbox.things.injection.modules.RainbowHatPeripheralsModule;


public class RPiMusicBoxApplication extends Application {

    private final RPiMusicBoxComponent component = createComponent();

    protected RPiMusicBoxComponent createComponent() {
        if (component == null) {
            return DaggerRPiMusicBoxComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .homeModule(new HomeModule())
                    .rainbowHatPeripheralsModule(new RainbowHatPeripheralsModule())
                    .nearbyModule(new NearbyModule())
                    .build();
        } else {
            throw new IllegalStateException("You can't recreate a component for RPiMusicBoxApplication");
        }
    }

    public RPiMusicBoxComponent getComponent() {
        return component;
    }
}
