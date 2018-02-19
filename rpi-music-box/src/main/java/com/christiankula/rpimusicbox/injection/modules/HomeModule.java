package com.christiankula.rpimusicbox.injection.modules;

import com.christiankula.rpimusicbox.home.HomeModel;
import com.christiankula.rpimusicbox.home.HomeMvp;
import com.christiankula.rpimusicbox.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    HomeMvp.Model provideModel() {
        return new HomeModel();
    }

    @Provides
    HomeMvp.Presenter providePresenter(HomeMvp.Model model) {
        return new HomePresenter(model);
    }
}
