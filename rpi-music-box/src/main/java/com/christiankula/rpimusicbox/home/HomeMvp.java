package com.christiankula.rpimusicbox.home;


import com.christiankula.rpimusicbox.mvp.BasePresenter;
import com.christiankula.rpimusicbox.mvp.BaseView;

public interface HomeMvp {

    interface Model {

    }

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

    }

}
