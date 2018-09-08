package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/618:51
 * desc   :
 * version: 1.0
 */
public class ZyCloudStatus implements Serializable{


    /**
     * status : 200
     * message : 成功
     * data : 0
     * code : 200
     */

    private int status;
    private String message;
    private int data;
    private int code;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
