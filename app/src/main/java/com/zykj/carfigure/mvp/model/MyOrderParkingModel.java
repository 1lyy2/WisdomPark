package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IMyOrderParkingModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/610:57
 * desc   :
 * version: 1.0
 */
public class MyOrderParkingModel implements IMyOrderParkingModel {
    @Override
    public void getMyOrderParkingList(Observer observer, int user_id, int order_statu) {
        RetrofitUtils.getApiService().getMyOrderParkingList(user_id,order_statu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
