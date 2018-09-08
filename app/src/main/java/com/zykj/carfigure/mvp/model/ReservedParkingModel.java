package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IReservedParkingModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/514:17
 * desc   :
 * version: 1.0
 */
public class ReservedParkingModel implements IReservedParkingModel {
    @Override
    public void reservedParking(Observer observer, int user_id, String licensePlateNumber, int street_id, String parkNumber, String order_time, String linkman, String linkPhone,String streetName) {
        RetrofitUtils.getApiService().reserveParking(user_id, licensePlateNumber, street_id, parkNumber, order_time, linkman, linkPhone,streetName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
