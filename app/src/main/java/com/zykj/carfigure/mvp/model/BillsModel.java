package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IBillsModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/817:03
 * desc   :
 * version: 1.0
 */
public class BillsModel implements IBillsModel {

    @Override
    public void getUserBills(Observer observer, int state) {
        RetrofitUtils.getApiService().getUserBills(state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
