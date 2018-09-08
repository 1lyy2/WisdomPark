package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.login.LoginActivity;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.mvp.presenter.UserLoginPresenter;
import com.zykj.carfigure.mvp.view.IUserLoginView;
import com.zykj.carfigure.utils.Constant;
import com.zykj.carfigure.utils.GlideUtil;
import com.zykj.carfigure.widget.AlertDialog;

import java.io.File;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

//设置界面
public class SettingActivity extends UserBaseActivity implements IUserLoginView {

    @BindView(R.id.rel_downMap)
    RelativeLayout relDownMap;
    @BindView(R.id.rel_message_reminding)
    RelativeLayout relMessageReminding;
    @BindView(R.id.rel_safe)
    RelativeLayout relSafe;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.rel_clean_cash)
    RelativeLayout relCleanCash;
    AlertDialog dialog;
    private UserLoginPresenter userLoginPresenter;

    @Override
    public void onCreatePresenter() {
        userLoginPresenter = new UserLoginPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        showCacheSize();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.setting_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick({R.id.rel_downMap, R.id.rel_message_reminding, R.id.rel_safe, R.id.rel_clean_cash, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_downMap:
                break;
            case R.id.rel_message_reminding:
                break;
            case R.id.rel_safe:
                break;
            case R.id.rel_clean_cash:
                dialog = new AlertDialog(this).builder().setTitle("清除缓存")
                        .setMsg("是否清楚缓存？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cleanCache();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog.show();
                break;
            case R.id.btn_logout:
                logout();
                break;

            default:
                break;
        }
    }

    /**
     * 缓存
     */
    private void showCacheSize() {
        new AsyncTask<String, String, String>() {

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                tvCache.setText(result);
            }

            @Override
            protected String doInBackground(String... params) {
                BigDecimal bg = new BigDecimal(GlideUtil.getFolderSize(new File(Constant.FilePath.GLIDE_CACHE_DIR)));
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return f1 + "MB";
            }
        }.execute("");
    }

    private void cleanCache() {
        if (tvCache.getText().toString().equals("0.0MB")) {
            showToastMsgShort("没有缓存");
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        showLoadingView(null, null);
        new AsyncTask<String, String, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                GlideUtil.cleanLocalCache();
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                showCacheSize();
                if (result) {
                    showToastMsgShort("清除缓存成功");
                } else {
                    showToastMsgShort("清除缓存成功");
                }
                hideLoadingView();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        };
    }

    private void logout() {
        userLoginPresenter.logout();
    }

    @Override
    public void loginSuccess(User user) {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void logoutSuccess(CommonBack commonBack) {
        hideLoadingView();
        if (commonBack == null) return;
        if (commonBack.getStatus() == 200) {
            showToastMsgShort("退出登录成功");
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            showToastMsgShort("退出登录失败");
        }
    }

    @Override
    public void logoutFailed() {
        hideLoadingView();
        showToastMsgShort("退出登录失败");
    }
}
