package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.ZyCloudStatus;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/617:35
 * desc   :
 * version: 1.0
 */
public interface IZyCloudVIew {
    void sendCommandSuccess(CommonBack commonBack);
    void sendCommandFailed();
    void getCarParkStatusSuccess(ZyCloudStatus status);
    void getCarParkStatusFailed();
}
