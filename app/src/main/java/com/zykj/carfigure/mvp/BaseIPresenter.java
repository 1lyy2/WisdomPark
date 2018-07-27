package com.zykj.carfigure.mvp;
//基类控制器
public interface BaseIPresenter<V> {
    //mvp 绑定
    void attech(V view);
    //解绑
    void detech();
}
