package com.zykj.carfigure.activity;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

//反向寻车
public class ReverseCarActivity extends BaseActivity {

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reverse_car;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.reverse_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
