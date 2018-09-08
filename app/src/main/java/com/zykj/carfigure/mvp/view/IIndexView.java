package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.entity.Function;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2314:44
 * desc   : 首页控制器的
 * version: 1.0
 */
public interface IIndexView {
    void getBannerListSuccess(Banner banner);
    void getBannerListFailed();
    void getFunctionListSuccess(Function function);
    void getFunctionListFailed();
}
