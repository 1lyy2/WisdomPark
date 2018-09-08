package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IParkingRecordModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/716:58
 * desc   :
 * version: 1.0
 */
public class ParkingRecordModel implements IParkingRecordModel {
    @Override
    public void getParkingRecordList(Observer observer, int user_id) {
        RetrofitUtils.getApiService().getParkingRecordList(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
