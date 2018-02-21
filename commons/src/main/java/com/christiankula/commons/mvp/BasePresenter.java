package com.christiankula.commons.mvp;

public interface BasePresenter<V extends BaseView> {

    void onViewAttached(V view);

    void onViewDetached();
}
