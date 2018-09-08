package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2815:27
 * desc   :
 * version: 1.0
 */
public interface IStreetModel {
    /**
     * 获取当前位置附近scopenKM的街道
     *
     * @param observer
     * @param longitude 经度
     * @param latitude  纬度
     * @param scopen    范围
     */
    void getStreetList(Observer observer, double longitude, double latitude, int scopen);
}
