package com.zykj.carfigure.activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.utils.SPCache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    public static final String PAGE_NAME = "登录";
    @BindView(R.id.edit_login_username)
    public EditText mEditText_account;

    @BindView(R.id.edit_login_password)
    public EditText mEditText_password;

    @BindView(R.id.edit_login_password_light)
    public EditText mEditText_password_light;

    @BindView(R.id.check_login_password)
    public CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = SPCache.getObject(getContext(), PAGE_NAME, String.class);
        if (s != null) {
            mEditText_account.setText(s);
            mEditText_account.setSelection(s.length());
        } mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditText_password_light.setVisibility(View.VISIBLE);
                    mEditText_password.setVisibility(View.GONE);
                    mEditText_password_light.setSelection(mEditText_password_light.length());
                    mEditText_password_light.requestFocus();
                } else {
                    mEditText_password.setVisibility(View.VISIBLE);
                    mEditText_password_light.setVisibility(View.GONE);
                    mEditText_password.setSelection(mEditText_password.length());
                    mEditText_password.requestFocus();
                }
            }
        });

        mEditText_account.addTextChangedListener(new TextWatcher() {
            boolean isAlert = true;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().contains("@")){ // 在文本改变前检查是否已经包含@字符
                    isAlert = false;  //包含 说明是已经有@
                }else {
                    isAlert = true; // 不包含 说明是新输入@
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(isAlert && str.substring(str.length()-1>0?str.length()-1:0, str.length()).equals("@")){
                   //弹出验证
                }
            }
        });

        mEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText_password_light.getVisibility() == View.GONE) {
                    mEditText_password_light.setText(s.toString());
                }
            }
        });

        mEditText_password_light.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText_password.getVisibility() == View.GONE) {
                    mEditText_password.setText(s.toString());
                }
            }
        });


    }
    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }
    @Override
    public void onCreatePresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.user_login);
    }

    @Override
    protected Context getContext() {
        return this;
    }
    /**
     * 点击登录按钮
     * @param v
     */
    @OnClick(R.id.login_submit)
    public void onLoginSubmit(View v) {
        launchActivity(MainActivity.class);
       //onLogin();
    }

    //点击登录
    public void onLogin() {
        String account = mEditText_account.getText().toString();
        String password = mEditText_password.getText().toString();
        String notice = "请输入";
        if (account.trim().length() == 0) {
            notice += "用户名";
        }
        if (password.trim().length() == 0) {
            notice += notice.length() > 3 ? "和密码" : "密码";
        }

        if (notice.length() > 3) {
            showToastMsgShort(notice);
            return;
        } else {
            startLogin(account, password);
        }
    }
    /**
     * 点击返回
     * @param v
     */
    @OnClick(R.id.btn_return)
    public void onBackFinish(View v){
        finish();
    }
    /* 数据
     *-----------------------------------------------------------------*/
    // 开始普通登录
    private void startLogin(String account, String password) {
     /*   stopLogin();

        showLoadingView(null, null, false);
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        params.put("password", getMD5String(password));
        params.put("token", XGHelper.getXGToken(getContext()));

        request_login = new JsonObjectRequest(Method.POST, app.interfaceURL.httpURL.loginUrl, loginSuccessRequestListener, loginErrorRequestListener, params, this);
        mRequestQueue.add(request_login);*/
       launchActivity(MainActivity.class);
    }
    @OnClick(R.id.register_text)
    public void onRegister(View view){
        launchActivity(RegisterActivity.class);
    }


}
