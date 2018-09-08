package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.User;

public interface IUserLoginView {
    void loginSuccess(User user);
    void loginFailed();
    void logoutSuccess(CommonBack commonBack);
    void logoutFailed();
}
