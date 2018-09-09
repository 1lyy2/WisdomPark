package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.Bills;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/816:49
 * desc   :
 * version: 1.0
 */
public interface IBillsView {
     void getUserBillsSuccess(Bills bills);
     void getUserBillsFailed();
}
