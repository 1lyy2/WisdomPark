package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IUserRegisterModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2210:20
 * desc   :
 * version: 1.0
 */
public class UserRegisterModel implements IUserRegisterModel {
    @Override
    public void getVerificationCode(Observer observer,String phone) {
        RetrofitUtils.getApiService().getVerificationCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void register(Observer observer,String username,String password,String code) {
              RetrofitUtils.getApiService().userRegister(username,password,code)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(observer);
    }

    @Override
    public void resetPassword(Observer observer, String username, String password) {
        RetrofitUtils.getApiService().resetPassword(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
