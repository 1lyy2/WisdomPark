package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CarPark;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3014:47
 * desc   : 车位
 * version: 1.0
 */
public interface ICarParkView {
    void getCarParkDetailSuccess(CarPark carPark);
    void getCarParkDetailFailed();
    void getFreeCarParkListSuccess(CarPark carPark);
    void getFreeCarParkListFailed();
}
