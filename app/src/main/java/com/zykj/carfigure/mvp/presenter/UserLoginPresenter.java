package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.UserLoginModel;
import com.zykj.carfigure.mvp.view.IUserLoginView;

import java.lang.ref.SoftReference;

public class UserLoginPresenter implements BaseIPresenter<IUserLoginView> {
    private UserLoginModel                mUserLoginModel;
    //使用软引用
    private SoftReference<IUserLoginView> softReference;

    public UserLoginPresenter(IUserLoginView softReference) {
        mUserLoginModel = new UserLoginModel();
        attech(softReference);
    }

    @Override
    public void attech(IUserLoginView view) {
        softReference = new SoftReference<IUserLoginView>(view);

    }

    @Override
    public void detech() {
        softReference.clear();
    }

}
