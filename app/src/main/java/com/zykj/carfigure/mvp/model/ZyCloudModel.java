package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IZyCloudModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/617:39
 * desc   :
 * version: 1.0
 */
public class ZyCloudModel implements IZyCloudModel {
    @Override
    public void sendCommand(Observer observer, String decId, int status) {
        RetrofitUtils.getApiService().senCommand(decId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getCarParkStatus(Observer observer, String devId) {
        RetrofitUtils.getApiService().getCarParkingStatus(devId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
