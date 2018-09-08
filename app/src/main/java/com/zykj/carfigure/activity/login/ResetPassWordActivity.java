package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.VerificationCode;
import com.zykj.carfigure.mvp.presenter.UserRegisterPresenter;
import com.zykj.carfigure.mvp.view.IUserRegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPassWordActivity extends BaseActivity implements IUserRegisterView {


    @BindView(R.id.edt_reset_psw)
    EditText edtResetPsw;
    @BindView(R.id.edt_reset_psw_ph)
    EditText edtResetPswPh;
    @BindView(R.id.btn_findpsw)
    Button btnFindpsw;
    private UserRegisterPresenter userRegisterPresenter;
    private String username;
    private String passWord;

    @Override
    public void onCreatePresenter() {
        userRegisterPresenter = new UserRegisterPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reset;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.reset_password_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void getVerificationCodeSucess(VerificationCode code) {

    }

    @Override
    public void getVerificationCodeFailed() {

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
            Intent intent = new Intent(ResetPassWordActivity.this,LoginActivity.class);
            intent.putExtra("loginUserName",username);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void resetPasswordFailed() {
        showToastMsgShort("重置密码失败");
    }

    @OnClick(R.id.btn_findpsw)
    public void onViewClicked() {
        resetPsw();
    }
    private void resetPsw(){
        String psw = edtResetPsw.getText().toString().trim();
        String psw_two = edtResetPswPh.getText().toString().trim();
        if(psw==null||psw.equals("")){
            showToastMsgShort("请输入新密码");
            return;
        }
        if(psw_two==null||psw_two.equals("")){
            showToastMsgShort("请再次输入新密码");
            return;
        }
        if(!psw.equals(passWord)){
            showToastMsgShort("请输入一样的新密码");
            return;
        }
        userRegisterPresenter.resetPassword(username,psw);
    }
}
