package com.zykj.carfigure.mvp.model.interface_model;


import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2314:30
 * desc   : 首页
 * version: 1.0
 */
public interface IIndexModel {
    //获取banner
    void getBannerList(Observer observer);
    //获取功能列表
    void getNavitionList(Observer observer);
}
