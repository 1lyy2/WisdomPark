package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.os.Bundle;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

public class FindPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableSupportActionBar();
    }

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.findpsw_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

}
