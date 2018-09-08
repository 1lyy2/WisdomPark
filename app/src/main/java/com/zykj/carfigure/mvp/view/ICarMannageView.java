package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CommonBack;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/249:41
 * desc   : 车辆管理
 * version: 1.0
 */
public interface ICarMannageView {
    void addCarSuccess(CommonBack commonBack);
    void addCarFailed();
    void deleteCarSuccess(CommonBack commonBack);
    void deleteCarFailed();
    void updateCarSuccess(CommonBack commonBack);
    void getUserCarListSuccess(CarMessage carMessage);
    void getUserCarListFailed();

}
