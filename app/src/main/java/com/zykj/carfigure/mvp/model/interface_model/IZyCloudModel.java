package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/617:37
 * desc   :
 * version: 1.0
 */
public interface IZyCloudModel {
    void sendCommand(Observer observer, String decId,int status);
    void getCarParkStatus(Observer observer,String devId);
}
