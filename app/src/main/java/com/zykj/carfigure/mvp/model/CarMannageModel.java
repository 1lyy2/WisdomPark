package com.zykj.carfigure.mvp.model;

import com.zykj.carfigure.http.RetrofitUtils;
import com.zykj.carfigure.mvp.model.interface_model.ICarMannageModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/249:40
 * desc   :
 * version: 1.0
 */
public class CarMannageModel implements ICarMannageModel {
    @Override
    public void addCar(Observer observer, RequestBody requestBody) {
        RetrofitUtils.getApiService().addCar(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void deleteCar(Observer observer, int carId) {
        RetrofitUtils.getApiService().deleteCar(carId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void updateCar(Observer observer, int carId) {

    }

    @Override
    public void getUserCarList(Observer observer, int user_id) {
        RetrofitUtils.getApiService().getUserCarList(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
