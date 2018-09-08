package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2015:13
 * desc   : 充值方式
 * version: 1.0
 */
public class RechargeType implements Serializable {
    private  int imgResurce;
    private  String typeName;
    private  int type;

    public RechargeType(int imgResurce, String typeName, int type) {
        this.imgResurce = imgResurce;
        this.typeName = typeName;
        this.type = type;
    }

    public int getImgResurce() {
        return imgResurce;
    }

    public void setImgResurce(int imgResurce) {
        this.imgResurce = imgResurce;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
