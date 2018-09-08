package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IStopCarModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/410:23
 * desc   : 我要停车
 * version: 1.0
 */
public class StopCarModel implements IStopCarModel {
    @Override
    public void findNearCarPark(Observer observer, double userLatitude, double userLongitude) {
        RetrofitUtils.getApiService().findNearCarPark(userLatitude, userLongitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void stopCar(Observer observer, int user_id, String carParkNumber, String licensePlateNumber) {
        RetrofitUtils.getApiService().stopCarPark(user_id, carParkNumber, licensePlateNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
