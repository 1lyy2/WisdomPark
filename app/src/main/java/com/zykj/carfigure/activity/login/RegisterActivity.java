package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.content.Intent;
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
import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.VerificationCode;
import com.zykj.carfigure.mvp.presenter.UserRegisterPresenter;
import com.zykj.carfigure.mvp.view.IUserRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<UserRegisterPresenter> implements IUserRegisterView {

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
    private UserRegisterPresenter userRegisterPresenter;
    private String phone;
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
        userRegisterPresenter = new UserRegisterPresenter(this);
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
                //获取验证码
                getVerificationCode();
                break;
            case R.id.btn_register:
                //注册
                register();
                break;
            case R.id.tv_useragreement:
                break;
        }
    }

    private void getVerificationCode() {
        phone = edtPhone.getText().toString().trim();
        if (phone == null || phone.equals("")) {
            showToastMsgShort("请输入手机号码");
        } else {
            if (userRegisterPresenter == null) return;
            userRegisterPresenter.getVerificationCode(phone);
        }
    }

    private void register() {
        phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        if (phone == null || phone.equals("")) {
            showToastMsgShort("请输入手机号码");
            return;
        }
        if (password == null || password.equals("")) {
            showToastMsgShort("请输入密码");
            return;
        }
        if(password.length()<6){
            showToastMsgShort("请输入至少6位数密码");
            return;
        }
        if (code == null || code.equals("")) {
            showToastMsgShort("请输入验证码");
            return;
        }
        userRegisterPresenter.registerUser(phone, password, code);
    }

    //获取验证码成功
    @Override
    public void getVerificationCodeSucess(VerificationCode code) {
        if (code == null) return;
        if(code.getStatus()==200){
            showToastMsgShort("验证码---------------------------->" + code.getData());
            edtCode.setText(code.getData());
        }else{
            showToastMsgShort(code.getMessage());
        }

    }

    //获取验证码失败
    @Override
    public void getVerificationCodeFailed() {
        showToastMsgShort("获取验证码失败");
    }

    //注册成功
    @Override
    public void registerSucess(Register register) {
        if(register==null) return;
        if(register.getStatus()==200){
            showToastMsgShort("注册成功");
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("username", phone);
            //设置返回数据
            setResult(1, intent);
            finish();
        }else{
            showToastMsgShort(register.getMessage());
        }

    }

    //注册失败
    @Override
    public void registerFailed() {
        showToastMsgShort("注册失败,你输入的验证码不对");
    }

    @Override
    public void resetPasswordSuccess(Register register) {

    }

    @Override
    public void resetPasswordFailed() {

    }
}
