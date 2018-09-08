package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.IInitZyCloudModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/710:33
 * desc   : 初始化治业云
 * version: 1.0
 */
public class InitZyCloudModel implements IInitZyCloudModel {

    @Override
    public void initZyCloud(Observer observer, String devId, int type) {
        RetrofitUtils.getApiService().initZyCloud(devId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
