package com.christiankula.rpimusicbox.mvp;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
