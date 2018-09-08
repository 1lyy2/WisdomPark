package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3014:39
 * desc   : 车位管理
 * version: 1.0
 */
public interface ICarParkModel {
    /**
     * 获取某个车位的详细信息
     *
     * @param observer
     * @param carParkId 车位ID
     */
    void getCarParkDetail(Observer observer, int carParkId);

    /**
     * 获取某个街道ID的空闲车位或者是所有的车位
     *
     * @param observer
     * @param streetId 街道的ID
     */
    void getFreeCarParkList(Observer observer, int streetId,int state);

}
