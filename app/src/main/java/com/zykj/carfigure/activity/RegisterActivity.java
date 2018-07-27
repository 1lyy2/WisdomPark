package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        enableSupportActionBar();
    }

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.user_register);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
