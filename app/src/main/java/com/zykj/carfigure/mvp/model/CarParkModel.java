package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.ICarParkModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3014:46
 * desc   :
 * version: 1.0
 */
public class CarParkModel implements ICarParkModel{
    @Override
    public void getCarParkDetail(Observer observer, int carParkId) {
        
    }

    @Override
    public void getFreeCarParkList(Observer observer, int streetId,int state) {
        RetrofitUtils.getApiService().getFreeCarParkList(streetId,state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
