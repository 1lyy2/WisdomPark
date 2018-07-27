package com.zykj.carfigure.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zykj.carfigure.R;
import com.zykj.carfigure.app.MyApplication;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.utils.ToastManager;
import com.zykj.carfigure.views.ProgressDialog;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 */
public abstract class BaseFragment<P extends BaseIPresenter> extends Fragment implements BaseInterface {
    protected String tag = "";
    protected View          rootView;
    protected MyApplication app;

    private ProgressDialog mProgressDialog;
    /**
     * 标识view是否被被创建
     */
    protected boolean isPrepared = false;
    /**
     * 标识第一次lazyload执行完毕
     */
    protected boolean lasyLoadComplete = false;

    /**
     * 是否每次都执行lazyload
     */
    boolean isLasyLoadAllTheTime = false;
    private Unbinder mBind;
    private P   mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(tag,"Create");
        app = (MyApplication) getActivity().getApplication();
        // 若使用BindEventBus注解，则绑定EventBus
        if(this.getClass().isAnnotationPresent(BindEventBus.class)){
            EventBusUtils.register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == rootView){
            rootView = inflater.inflate(getContentViewId(), container, false);
            mBind = ButterKnife.bind(this,rootView);
            tag=getFragmentName();
            onCreatePresenter();
            initView(rootView);
        }
        Log.v(tag, "CreateView");
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 若使用BindEventBus注解，则解绑定EventBus
        if(this.getClass().isAnnotationPresent(BindEventBus.class)){
            EventBusUtils.unregister(this);
        }
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
        if(mBind!=null){
            mBind.unbind();
        }
        if(mPresenter!=null){
            mPresenter.detech();
        }
        Log.d(tag, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // isPrepared = false;//rootview已经缓存
        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    /**
     * 初始化view
     * @param rootView
     */
    protected abstract void initView(View rootView);

    /**
     * 获取layout IDs
     * @return
     */
    protected abstract int getContentViewId();
    /**
     * 获取名称
     *
     * @return
     */
    protected abstract String getFragmentName();

    /**
     * 当Fragment可见时，执行，且只执行一次
     * 在Fragment的onCreateView后，还不想执行的代码（例如网络访问）可以放在这里执行
     */
    protected abstract void onLazyLoad();
    public  abstract  void  onCreatePresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//是否可见，true表明显示在你眼前了
            if (isPrepared) {//view是否创建完成
                if (isLasyLoadAllTheTime) {//设置 为true时 每次执行懒加载，与onResume不同的是 搭配viewPager使用不会进行预加载
                    onLazyLoad();
                } else if (!lasyLoadComplete) {
                    onLazyLoad();
                    lasyLoadComplete = true;
                }
            }
        } else {

        }
    }


    protected void setLasyLoadEveryTime(boolean isLasyLoadAllTheTime) {
        this.isLasyLoadAllTheTime = isLasyLoadAllTheTime;
    }


    //region ToastMessage

    /**
     * 显示Toast信息
     *
     * @param ms
     */
    public void showToastMsgShort(String ms) {
        if (!getActivity().isFinishing()) {
            ToastManager.showShortToast(getActivity().getApplicationContext(), ms);
        }
    }

    public void showToastMsgLong(String ms) {
        if (!getActivity().isFinishing()) {
            ToastManager.showLongToast(getActivity().getApplicationContext(), ms);
        }
    }
    //endregion

    //region 进度展示框

    /**
     * 显示 加载框
     *
     * @param dismiss
     * @param cancel
     * @param canCancel  点击外面区域是否去掉LoadingView；true:可取消,false:不可
     */
    public void showLoadingView(DialogInterface.OnDismissListener dismiss, DialogInterface.OnCancelListener cancel, boolean canCancel) {
        try {
            if (!getActivity().isFinishing()) {
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
     * @param canCancel
     */
    private void initProgressDialog(DialogInterface.OnDismissListener dismiss, DialogInterface.OnCancelListener cancel, boolean canCancel) {
        mProgressDialog = ProgressDialog.createDialog(getActivity());
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
            if (!getActivity().isFinishing()) {
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
     * @param isError
     */
    public void isLoadError(boolean isError){
        if(rootView!=null && rootView.findViewById(R.id.load_error_view)!=null){
            if(isError){
                rootView.findViewById(R.id.load_error_view).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.load_error_text).setVisibility(View.VISIBLE);
            }else{
                rootView.findViewById(R.id.load_error_view).setVisibility(View.GONE);
                rootView.findViewById(R.id.load_error_text).setVisibility(View.INVISIBLE);
            }

        };
    }
    /**
     * @param response
     * @return
     */
    @Override
    public boolean is201(JSONObject response,boolean isToast) {
   /*     if (response != null && response.optInt(Constant.AttributeName.CODE) == Constant.ResultCode.SUCCESS) {
            return true;
        } else {
            if (response != null && isToast)
                showToastMsgShort(response.optString(Constant.AttributeName.MSG));
        }*/
        return false;

    }

    @Override
    public void onBackPressed() {

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isPrepared && getUserVisibleHint()) {
            onLazyLoad();
            lasyLoadComplete = true;
        }
        isPrepared = true;
    }


    /**
     * 启动Activity
     */
    public void launchActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getContext(), cls));
    }

}
