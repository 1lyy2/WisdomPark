package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/610:56
 * desc   : 我的预约
 * version: 1.0
 */
public interface IMyOrderParkingModel {
    void getMyOrderParkingList(Observer observer,int user_id,int order_statu);
}
