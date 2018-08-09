package com.zykj.carfigure.activity.me;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

//投诉建议
public class SuggestActivity extends BaseActivity {

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.suggest_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
