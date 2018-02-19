package com.christiankula.rpimusicbox.mvp;

public interface BasePresenter<V extends BaseView> {

    void onViewAttached(V view);

    void onViewDetached();
}
