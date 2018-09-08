package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2215:00
 * desc   : 验证码
 * version: 1.0
 */
public class VerificationCode implements Serializable{

    /**
     * code : 200
     * message : SUCCESS
     * data : 003362
     */

    private int status;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
