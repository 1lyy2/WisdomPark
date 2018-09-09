package com.zykj.carfigure.mvp.model.interface_model;

import io.reactivex.Observer;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/817:02
 * desc   :
 * version: 1.0
 */
public interface IBillsModel  {
    void getUserBills(Observer observer, int state);
}
