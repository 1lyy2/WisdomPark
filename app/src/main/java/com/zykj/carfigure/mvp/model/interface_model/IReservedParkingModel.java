package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/514:04
 * desc   :
 * version: 1.0
 */
public interface IReservedParkingModel {
    /**
     * 预约车位
     *
     * @param observer
     * @param user_id            用户id
     * @param licensePlateNumber 车牌号
     * @param street_id          街道id
     * @param parkNumber         车位编号
     * @param order_time         预约时间
     * @param linkman            联系人
     * @param linkPhone          联系人电话
     */
    void reservedParking(Observer observer, int user_id, String licensePlateNumber,
                         int street_id, String parkNumber, String order_time, String linkman, String linkPhone,String streetName);
}
