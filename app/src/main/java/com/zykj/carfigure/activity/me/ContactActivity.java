package com.zykj.carfigure.activity.me;

import android.content.Context;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
//联系我们

public class ContactActivity extends BaseActivity {


    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView() {
     enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.contact_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
