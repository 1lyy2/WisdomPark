package com.zykj.carfigure.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.app.MyApplication;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.utils.StatusBarUtil;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.views.ProgressDialog;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * create by weimian
 */
public abstract class BaseActivity<P extends BaseIPresenter> extends AppCompatActivity implements BaseInterface {
    protected String TAG = "";
    protected MyApplication app;
    private ProgressDialog mProgressDialog;
    private Unbinder bind;
    private P mPresenter;

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public int statusBarColor = 0;

    public void setDisableStatusBar(boolean disableStatusBar) {
        this.disableStatusBar = disableStatusBar;
    }

    //控制是否消除顶部状态栏区域
    private boolean disableStatusBar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        // 若使用BindEventBus注解，则绑定EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        app = (MyApplication) getApplication();
        TAG = getClass().getSimpleName();
        bind = ButterKnife.bind(this);
        app.addActivity(this);
        if (!disableStatusBar) {
            StatusBarUtil.setColor(this,getResources().getColor(R.color.white),50);
        } else {
            setStatusBar();
        }
        onCreatePresenter();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void finish() {
        app.removeActivity(this);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // 若使用BindEventBus注解，则解绑定EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        //解除绑定
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detech();
        }
        super.onDestroy();
        Log.d(TAG, "Activity-onDestroy");
    }

    public abstract void onCreatePresenter();

    public void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                || StatusBarUtil.isMIUI()
                || StatusBarUtil.isFlyme()) {
            //允许修改状态栏字体图标颜色，则改字体图标为深色
            StatusBarUtil.setLightMode(this);
            statusBarColor = this.getResources().getColor(R.color.common_background);
        } else {
            statusBarColor = this.getResources().getColor(R.color.color_gray_transparent);
        }
    }

    /**
     * 关联布局ID
     *
     * @return
     */
    public abstract int getContentViewResId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 获取activity名称，编写Activity的名称
     *
     * @return
     */
    protected abstract String getActivityName();

    /**
     * 返回Activity的对象。this
     *
     * @return
     */
    protected abstract Context getContext();


    //region ToastMessage

    /**
     * 显示Toast信息
     *
     * @param ms
     */
    public void showToastMsgShort(String ms) {
        if (!isFinishing()) {
            ToastManager.showShortToast(getApplicationContext(), ms);
        }
    }

    public void showToastMsgLong(String ms) {
        if (!isFinishing()) {
            ToastManager.showLongToast(getApplicationContext(), ms);
        }
    }
    //endregion

    //region 进度展示框

    /**
     * 显示 加载框
     *
     * @param dismiss
     * @param cancel
     * @param canCancel 点击外面区域是否去掉LoadingView；true:可取消,false:不可
     */
    public void showLoadingView(DialogInterface.OnDismissListener dismiss, DialogInterface.OnCancelListener cancel, boolean canCancel) {
        try {
            if (!isFinishing()) {
                if (mProgressDialog == null) {
                    initProgressDialog(dismiss, cancel, canCancel);
                    mProgressDialog.show();
                } else if (!mProgressDialog.isShowing()) {
                    mProgressDialog.setCanceledOnTouchOutside(canCancel);
                    mProgressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化加载中对话框
     *
     * @param dismiss
     * @param cancel
     * @param canCancel true:可以取消的进度框，false不可以取消的进度框
     */

    private void initProgressDialog(DialogInterface.OnDismissListener dismiss, DialogInterface.OnCancelListener cancel, boolean canCancel) {
        mProgressDialog = ProgressDialog.createDialog(getContext());
        mProgressDialog.setCanceledOnTouchOutside(canCancel);
        if (dismiss != null) {
            mProgressDialog.setOnDismissListener(dismiss);
        }
        if (cancel != null) {
            mProgressDialog.setOnCancelListener(cancel);
        }

    }

    @Override
    public void showLoadingView(DialogInterface.OnDismissListener dismiss, DialogInterface.OnCancelListener cancel) {
        showLoadingView(dismiss, cancel, true);// 默认点击外面可以取消
    }

    @Override
    public void hideLoadingView() {
        try {
            if (!isFinishing()) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog.setCanceledOnTouchOutside(true);// 恢复默认状态，因为用的是单实例
                }
            }
        } catch (Exception e) {

        }
    }
    //endregion

    /**
     * 加载失败 出现失败界面，需要在布局文件引入view_load_error文件
     * 并且实现onReload方法 调用加载数据接口
     *
     * @param isError
     */
    public void isLoadError(boolean isError) {
        if (!isFinishing() && findViewById(R.id.load_error_view) != null) {
            if (isError) {
                findViewById(R.id.load_error_view).setVisibility(View.VISIBLE);
                findViewById(R.id.load_error_text).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.load_error_view).setVisibility(View.GONE);
                findViewById(R.id.load_error_text).setVisibility(View.INVISIBLE);
            }

        }
        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * @param response
     * @return
     */
    @Override
    public boolean is201(JSONObject response, boolean isToast) {

        return false;
    }

    @Override
    public void setSupportActionBar(@Nullable final Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.common_title_text));
        toolbar.setTitle("");
        TextView customTitle = (TextView) findViewById(R.id.comm_title_tool_bar);
        if (customTitle != null) {
            customTitle.setText(getActivityName());
        }
        setTitle("");
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView customTitle = (TextView) findViewById(R.id.comm_title_tool_bar);
        if (customTitle == null || TextUtils.isEmpty(title)) {
            super.setTitle(title);
        } else {
            super.setTitle("");
            customTitle.setText(title);
        }

    }

    /**
     * 启用toolbar为actionbar
     * 使用方法：
     * 1、在布局中
     * <include layout="@layout/common_tool_bar"></include>
     * 2、使用该方法设置SupportActionBar
     */
    protected void enableSupportActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.id_toolbar));
    }


    /**
     * 启动Activity
     */
    public void launchActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    public void onBackPressed() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                finish();
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
