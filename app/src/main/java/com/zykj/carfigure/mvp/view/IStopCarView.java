package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.NearCarPark;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/410:17
 * desc   : 我要停车
 * version: 1.0
 */
public interface IStopCarView {
    void findNearCarParkSuccess(NearCarPark nearCarPark);

    void findNearCarParkFailed();

    void stopCarSuccess(CommonBack commonBack);

    void stopCarFailed();

}
