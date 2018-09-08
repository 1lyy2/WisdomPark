package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/410:20
 * desc   :
 * version: 1.0
 */
public interface IStopCarModel {
    void findNearCarPark(Observer observer, double userLatitude, double userLongitude);

    void stopCar(Observer observer, int user_id, String carParkNumber, String licensePlateNumber);
}
