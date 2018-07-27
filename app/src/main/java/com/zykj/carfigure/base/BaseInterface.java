package com.zykj.carfigure.base;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;

import org.json.JSONObject;

public interface BaseInterface {

    /**
     * 显示loading view
     */
    public void showLoadingView(OnDismissListener dismiss, OnCancelListener Cancel);


    /**
     * 隐藏loading view
     */
    public void hideLoadingView();

    /**
     * 显示短消息
     *
     * @param ms
     */
    public void showToastMsgShort(String ms);

    /**
     * 显示长消息
     *
     * @param ms
     */
    public void showToastMsgLong(String ms);

    /**
     * 判断服务器返回值是否等于201，不等于则自动显示Toast信息
     *
     * @param response
     * @return
     */
    public boolean is201(JSONObject response, boolean isToast);


    public void onBackPressed();
}
