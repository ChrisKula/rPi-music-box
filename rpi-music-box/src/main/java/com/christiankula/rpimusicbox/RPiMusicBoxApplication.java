package com.christiankula.rpimusicbox;

import android.app.Application;

import com.christiankula.rpimusicbox.injection.components.DaggerRPiMusicBoxComponent;
import com.christiankula.rpimusicbox.injection.components.RPiMusicBoxComponent;
import com.christiankula.rpimusicbox.injection.modules.RainbowHatPeripheralsModule;


public class RPiMusicBoxApplication extends Application {

    private final RPiMusicBoxComponent component = createComponent();

    protected RPiMusicBoxComponent createComponent() {
        if (component == null) {
            return DaggerRPiMusicBoxComponent.builder()
                    .rainbowHatPeripheralsModule(new RainbowHatPeripheralsModule())
                    .build();
        } else {
            throw new IllegalStateException("You can't recreate a component for RPiMusicBoxApplication");
        }
    }

    public RPiMusicBoxComponent getComponent() {
        return component;
    }
}
