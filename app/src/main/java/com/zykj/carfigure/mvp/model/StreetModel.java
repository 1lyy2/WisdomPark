package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IStreetModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2815:45
 * desc   : 街道
 * version: 1.0
 */
public class StreetModel implements IStreetModel {
    @Override
    public void getStreetList(Observer observer, double latitude, double longitude, int scopen) {
        RetrofitUtils.getApiService().getStreetList(latitude, longitude, scopen)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
