package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2317:16
 * desc   : 车辆管理
 * version: 1.0
 */
public interface ICarMannageModel {
    //添加车辆
    void addCar(Observer observer, RequestBody requestBody);
    //删除车辆
    void deleteCar(Observer observer,int carId);
    //更新车辆
    void updateCar(Observer observer,int carId);
    //获取当前用户的车辆信息
    void getUserCarList(Observer observer,int user_id);
}
