package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.Street;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2815:50
 * desc   :
 * version: 1.0
 */
public interface IStreetView {
    void getStreetListSuccess(Street street);
    void getStreetListFailed();
}
