package com.zykj.carfigure.mvp.view;
import com.zykj.carfigure.entity.Simple;
import com.zykj.carfigure.mvp.BaseView;

public interface IUserLoginView extends BaseView {
    void loginSuccess(Simple simple);
    void loginFailed();
}
