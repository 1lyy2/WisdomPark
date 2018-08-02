package com.zykj.carfigure.wxapi;

/**
 */
public interface HttpCallBackListener {

    void onFinish(String response);

    void onError(Exception e);

}
