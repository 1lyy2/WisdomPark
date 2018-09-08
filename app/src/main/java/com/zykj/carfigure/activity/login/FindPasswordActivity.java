package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.VerificationCode;
import com.zykj.carfigure.mvp.presenter.UserRegisterPresenter;
import com.zykj.carfigure.mvp.view.IUserRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity implements IUserRegisterView {

    @BindView(R.id.edt_phone_find)
    EditText edtPhoneFind;
    @BindView(R.id.edt_code_find)
    EditText edtCodeFind;
    @BindView(R.id.tv_getcode_find)
    TextView tvGetcodeFind;
    @BindView(R.id.btn_findpsw)
    Button btnFindpsw;
    private UserRegisterPresenter userRegisterPresenter;
    private String findCode;//服务端返回的验证码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePresenter() {
        userRegisterPresenter = new UserRegisterPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_find_password;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.findpsw_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void getVerificationCodeSucess(VerificationCode code) {
        if (code == null) return;
        if (code.getStatus() == 200) {
            findCode = code.getData();
            edtCodeFind.setText(findCode);
        } else {
            showToastMsgShort("获取验证码失败：" + code.getMessage());
        }
    }

    @Override
    public void getVerificationCodeFailed() {
        showToastMsgShort("获取验证码失败");
    }

    @Override
    public void registerSucess(Register register) {

    }

    @Override
    public void registerFailed() {

    }

    @Override
    public void resetPasswordSuccess(Register register) {
        if (register == null) return;
        if (register.getStatus() == 200) {
            showToastMsgShort("重置密码成功");
            finish();
        }
    }

    @Override
    public void resetPasswordFailed() {
        showToastMsgShort("重置密码失败");
    }

    @OnClick({R.id.tv_getcode_find, R.id.btn_findpsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode_find:
                //获取验证码
                getCode();
                break;
            case R.id.btn_findpsw:
                resetpsw();
                break;
        }
    }

    //获取验证码
    private void getCode() {
        String phone = edtPhoneFind.getText().toString().trim();
        if (phone == null || phone.equals("")) {
            showToastMsgShort("请输入手机号码");
        } else {
            userRegisterPresenter.getVerificationCode(phone);
        }
    }

    //重置密码
    private void resetpsw() {
        String phone = edtPhoneFind.getText().toString().trim();
        if (phone == null || phone.equals("")) {
            showToastMsgShort("请输入手机号码");
            return;
        }
        String code = edtCodeFind.getText().toString().trim();
        if (code == null || code.equals("")) {
            showToastMsgShort("请输入验证码");
            return;
        }
        if(findCode==code){
            Intent intent = new Intent();
            intent.putExtra("username",phone);
            intent.setClass(FindPasswordActivity.this,ResetPassWordActivity.class);
            startActivity(intent);
        }else{
            showToastMsgShort("输入的验证码不正确");
        }

    }


}
