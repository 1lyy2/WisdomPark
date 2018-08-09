package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserLoginModel implements IUserLoginModel {
    @Override
    public void userLogin(Observer observer) {
        RetrofitUtils.getApiService().getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
