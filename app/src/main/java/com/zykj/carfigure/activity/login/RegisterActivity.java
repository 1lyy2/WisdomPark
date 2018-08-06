package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.comm_title_tool_bar)
    TextView commTitleToolBar;
    @BindView(R.id.common_right_img)
    ImageView commonRightImg;
    @BindView(R.id.id_toolbar)
    Toolbar idToolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_useragreement)
    TextView tvUseragreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //下划线
        tvUseragreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        tvUseragreement.getPaint().setAntiAlias(true);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.user_register);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick({R.id.tv_getcode, R.id.btn_register, R.id.tv_useragreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode:
                break;
            case R.id.btn_register:
                break;
            case R.id.tv_useragreement:
                break;
        }
    }
}
