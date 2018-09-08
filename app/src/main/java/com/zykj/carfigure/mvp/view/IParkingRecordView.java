package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.ParkingRecord;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/716:56
 * desc   :
 * version: 1.0
 */
public interface IParkingRecordView {
    void getParkingRecordListSuccess(ParkingRecord parkingRecord);
    void getParkingRecordListFailed();
}
