package com.zykj.carfigure.base;

import android.os.Bundle;

import com.zykj.carfigure.R;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2314:16
 * desc   : //需要登录的基类
 * version: 1.0
 */
public abstract  class UserBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!app.isLogined()) {
            app.gotoLogin();
            showToastMsgShort(getString(R.string.str_please_login));
            finish();
        }
    }
}
