package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.User;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/111:26
 * desc   : 第三方登录
 * version: 1.0
 */
public interface IUserLoginThirdView  {
    void thirdLoginSuccess(User user);

    void thirdLoginFailed();

    void WxUserInfo(String openid, String nickname, String avatar, int type);
}
