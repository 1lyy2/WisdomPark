package com.zykj.carfigure.activity.me;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

//常见问题
public class CommonProblemActivity extends BaseActivity {


    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.common_problem_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
