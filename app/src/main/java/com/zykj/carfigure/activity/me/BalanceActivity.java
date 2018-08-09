package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;

//余额
public class BalanceActivity extends BaseActivity {

    @BindView(R.id.common_right_text)
    TextView commonRightText;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_balance;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        commonRightText.setText("余额明细");
        commonRightText.setTextColor(getResources().getColor(R.color.commom_text_color));
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.balance_setting);
    }

    @Override
    protected Context getContext() {
        return this;
    }
}
