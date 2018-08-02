package com.zykj.carfigure.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.app.AppConfig;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.utils.SPCache;
import com.zykj.carfigure.utils.StrUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.wxapi.HttpCallBackListener;
import com.zykj.carfigure.wxapi.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.lin_weixin)
    LinearLayout linWeixin;
    @BindView(R.id.lin_QQ)
    LinearLayout linQQ;
    //登录类型
    private int type;
    /* QQ登录*/
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private String openid;
    private String nickname;
    private String avatar;
    //微信登录
    //IWAPI是第三方app和微信通讯的openapi接口
    private IWXAPI api;
    private ReceiveBroadCast receiveBroadCast;//获取授权回调信息@WxEntityActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = SPCache.getObject(getContext(), PAGE_NAME, String.class);
        if (s != null) {
            mEditText_account.setText(s);
            mEditText_account.setSelection(s.length());
        }
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                if (s.toString().contains("@")) { // 在文本改变前检查是否已经包含@字符
                    isAlert = false;  //包含 说明是已经有@
                } else {
                    isAlert = true; // 不包含 说明是新输入@
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (isAlert && str.substring(str.length() - 1 > 0 ? str.length() - 1 : 0, str.length()).equals("@")) {
                    //弹出验证
                }
            }
        });

        mEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiveBroadCast);
    }

    @Override
    protected void initView() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("authlogin");
        this.registerReceiver(receiveBroadCast, filter);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.user_login);
    }

    @Override
    protected Context getContext() {
        return this;
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
            //通过验证
        }
    }


    @OnClick(R.id.register_text)
    public void onRegister(View view) {
        launchActivity(RegisterActivity.class);
    }


    @OnClick({R.id.lin_weixin, R.id.lin_QQ, R.id.btn_return, R.id.login_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_weixin:
                //微信登录
                weixinLogin();
                break;
            case R.id.lin_QQ:
                //QQ第三方登录
                qqLogin();
                break;
            case R.id.btn_return:
                //点击 返回
                finish();
                break;
            case R.id.login_submit:
                //登录
                launchActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }

    //这里是调用QQ登录的关键代码
    public void qqLogin() {
        //这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        if (mTencent == null) {
            mTencent = Tencent.createInstance(AppConfig.QQ_APPID, LoginActivity.this.getApplicationContext());
        }
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        mTencent.login(LoginActivity.this, "all", mIUiListener);
    }

    //qq登录接口回调
    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {

            if (response == null) {
                showToastMsgLong("登录失败");
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                showToastMsgShort("登录失败");
                return;
            }
            showToastMsgShort("正在登录...");
            showLoadingView(null, null);
            doComplete(jsonResponse);
        }

        @Override
        public void onError(UiError uiError) {
            ToastManager.showShortToast(LoginActivity.this, "onError: " + uiError.errorDetail);
        }

        @Override
        public void onCancel() {
            ToastManager.showShortToast(LoginActivity.this, "取消登录");
        }
    }

    protected void doComplete(JSONObject values) {
        openid = values.optString("openid");
        /**到此已经获得OpneID以及其他你想获得的内容了
         QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
         sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
         如何得到这个UserInfo类呢？  */
        String accessToken = values.optString("access_token");
        String expires = values.optString("expires_in");
        mTencent.setOpenId(openid);
        mTencent.setAccessToken(accessToken, expires);

        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
        //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON
        info.getUserInfo(new IUiListener() {

            public void onComplete(final Object response) {
                // 返回Bitmap对象。
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    nickname = obj.optString("nickname");
                    avatar = obj.optString("figureurl_qq_2");
                    //showToastMsgLong(openid+"=="+nickname+"=="+avatar);
                    type = 3;//QQ类型登录
                    //obj.optString("gender");
                    //userLoginThirdPresenter.login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onCancel() {
                hideLoadingView();
            }

            public void onError(UiError arg0) {
                hideLoadingView();
            }

        });
    }

    /* 微信登录的关键代码*/

    public void weixinLogin() {
        showLoadingView(null, null);
        showToastMsgLong("正在授权...");
        if (api == null) {
            api = WXAPIFactory.createWXAPI(LoginActivity.this, AppConfig.WX_APPID, true);
            api.registerApp(AppConfig.WX_APPID);
        }
        if (!api.isWXAppInstalled()) {
            hideLoadingView();
            showToastMsgLong("您没有安装微信客户端");
            return;
        }
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());//可作为返回时的依据 传什么就返回什么 赞不用
        api.sendReq(req);
        hideLoadingView();
    }

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String code = intent.getStringExtra("code");
            Log.i("wxlogin", code);
            if (!StrUtil.isEmpty(code)) {
                //开启登录模式
                // userLoginThirdPresenter.getAccessToken(code);
                showToastMsgShort("开启登录");
                getAccessToken(code);
            }
        }
    }

    private void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + AppConfig.WX_APPID
                + "&secret="
                + AppConfig.WX_APPSecret
                + "&code="
                + code
                + "&grant_type=authorization_code";
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                //解析以及存储获取到的信息
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String access_token = jsonObject.getString("access_token");
                    String openid = jsonObject.getString("openid");
                    String refresh_token = jsonObject.getString("refresh_token");
                    getPersonMessage(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                //出错
                hideLoadingView();
            }
        });
    }
    private  void getPersonMessage(String access_token,String openid){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.sendHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String nickname = jsonObject.getString("nickname");
                    String openid = jsonObject.getString("openid");
                    int sex = jsonObject.optInt("sex",0);//1为男性，2为女性
                    String headimgurl = jsonObject.getString("headimgurl");
                    //在此实现微信登录操作
                    //userLoginThirdView.WxUserInfo(openid, nickname, headimgurl, 1);//1为微信类型
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

                //userLoginThirdView.getBaseInterface().hideLoadingView();
            }
        });
    }

}
