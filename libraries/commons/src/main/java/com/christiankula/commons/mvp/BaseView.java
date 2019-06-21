package com.christiankula.commons.mvp;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
