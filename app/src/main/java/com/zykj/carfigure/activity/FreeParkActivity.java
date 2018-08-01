package com.zykj.carfigure.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;

/**
 * 空车位列表
 */
public class FreeParkActivity extends BaseActivity {

    @BindView(R.id.common_right_img)
    ImageView commonRightImg;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_free_park;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        commonRightImg.setVisibility(View.VISIBLE);
        commonRightImg.setBackgroundResource(R.mipmap.ic_launcher_round);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.free_park_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
