package com.christiankula.rpimusicbox.things.injection.modules;

import com.christiankula.rpimusicbox.things.home.HomeModel;
import com.christiankula.rpimusicbox.things.home.HomeMvp;
import com.christiankula.rpimusicbox.things.home.HomePresenter;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionsClient;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    HomeMvp.Model provideModel(ConnectionsClient connectionsClient, AdvertisingOptions advertisingOptions) {
        return new HomeModel(connectionsClient, advertisingOptions);
    }

    @Provides
    HomeMvp.Presenter providePresenter(HomeMvp.Model model) {
        return new HomePresenter(model);
    }
}
