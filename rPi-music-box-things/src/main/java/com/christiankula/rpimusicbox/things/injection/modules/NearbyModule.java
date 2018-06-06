package com.christiankula.rpimusicbox.things.injection.modules;

import android.content.Context;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Strategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NearbyModule {

    @Provides
    @Singleton
    ConnectionsClient provideConnectionsClient(Context context) {
        return Nearby.getConnectionsClient(context);
    }

    @Provides
    @Singleton
    AdvertisingOptions provideAdvertisingOptions(){
        return new AdvertisingOptions(Strategy.P2P_STAR);
    }
}
