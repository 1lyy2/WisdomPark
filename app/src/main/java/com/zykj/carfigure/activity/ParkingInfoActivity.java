package com.zykj.carfigure.activity;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

/**
 * 停车资讯
 */
public class ParkingInfoActivity extends BaseActivity {


    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_parking_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.parking_info_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
