package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.Simple;
import com.zykj.carfigure.log.Log;
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

    public void getData() {
        mUserLoginModel.userLogin(new Observer<Simple>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("测试",d.toString());
            }

            @Override
            public void onNext(Simple simple) {
              softReference.get().loginSuccess(simple);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("测试",e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("结束","结束");
            }
        });
    }
}

