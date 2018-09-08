package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.ReservedParking;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/610:53
 * desc   : 我的预约
 * version: 1.0
 */
public interface IMyOrderParkingView {
    void getMyOrderParkingListSuccess(ReservedParking reservedParking);
    void getMyOrderParkingListFailed();
}
