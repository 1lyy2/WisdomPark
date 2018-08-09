package com.zykj.carfigure.activity.me;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

//添加车辆
public class AddCarActivity extends BaseActivity {


    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void initView() {
      enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.add_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
