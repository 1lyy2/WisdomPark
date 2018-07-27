package com.zykj.carfigure.mvp.view;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.mvp.BaseView;

public interface IUserLoginView extends BaseView {
    void loginSuccess(User user);
    void loginFailed();
}
