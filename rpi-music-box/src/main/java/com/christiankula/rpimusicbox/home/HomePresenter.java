package com.christiankula.rpimusicbox.home;

import javax.inject.Inject;

public class HomePresenter implements HomeMvp.Presenter {

    private HomeMvp.View view;

    private HomeMvp.Model model;

    @Inject
    public HomePresenter(HomeMvp.Model model) {
        this.model = model;
    }

    @Override
    public void onViewAttached(HomeMvp.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }
}
