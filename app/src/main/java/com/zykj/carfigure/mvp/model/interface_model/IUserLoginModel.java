package com.zykj.carfigure.mvp.model.interface_model;


import io.reactivex.Observer;

public interface IUserLoginModel {
    void userLogin(Observer observer,String username,String password);
    void userLogout(Observer observer);
}
