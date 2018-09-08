package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CommonBack;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/710:27
 * desc   : 初始化治业云
 * version: 1.0
 */
public interface IInitZyCloudView {
    void initZyCloudSuccess(CommonBack commonBack);
    void initZyCloudFailed();

}
