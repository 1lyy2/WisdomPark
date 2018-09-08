package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CommonBack;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/514:01
 * desc   : 预约车位
 * version: 1.0
 */
public interface IReservedParkingView {
    void reservedParkingSuccess(CommonBack commonBack);

    void reservedParkingFailed();

}
