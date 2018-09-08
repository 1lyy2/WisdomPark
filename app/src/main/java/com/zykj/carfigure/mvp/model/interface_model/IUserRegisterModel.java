package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2210:11
 * desc   :  用户注册s
 * version: 1.0
 */
public interface IUserRegisterModel {
    //获取验证码
    void getVerificationCode(Observer observer,String phone);
    //用户注册
    void register(Observer observer,String username,String password,String code);
    //重置密码
    void resetPassword(Observer observer,String username,String password);
}
