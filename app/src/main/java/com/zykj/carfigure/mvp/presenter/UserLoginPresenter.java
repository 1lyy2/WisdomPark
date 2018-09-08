package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.UserLoginModel;
import com.zykj.carfigure.mvp.view.IUserLoginView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserLoginPresenter implements BaseIPresenter<IUserLoginView> {
    private UserLoginModel mUserLoginModel;
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

    //用户登录
    public void login(String username, String password) {
        mUserLoginModel.userLogin(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                softReference.get().loginSuccess(user);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().loginFailed();
            }

            @Override
            public void onComplete() {

            }
        }, username, password);
    }

    //用户退出登录
    public void logout() {
        mUserLoginModel.userLogout(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().logoutSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().logoutFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }


}

