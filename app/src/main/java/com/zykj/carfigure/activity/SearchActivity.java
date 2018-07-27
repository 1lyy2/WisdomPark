package com.zykj.carfigure.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.img_return)
    public ImageView img_return;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        getSupportActionBar().hide();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.search_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
    @OnClick({R.id.img_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
        }
    }
}
