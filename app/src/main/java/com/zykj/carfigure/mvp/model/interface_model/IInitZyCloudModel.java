package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/710:29
 * desc   :
 * version: 1.0
 */
public interface IInitZyCloudModel {
    void initZyCloud(Observer observer,String devId, int type);

}
