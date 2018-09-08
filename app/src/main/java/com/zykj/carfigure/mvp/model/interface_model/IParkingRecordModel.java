package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/716:57
 * desc   :
 * version: 1.0
 */
public interface IParkingRecordModel {
    void getParkingRecordList(Observer observer, int user_id);
}
