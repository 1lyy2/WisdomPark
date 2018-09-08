package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1711:57
 * desc   :
 * version: 1.0
 */
public class AddressBean implements Serializable {
    private double longitude;//经度
    private double latitude;//纬度
    private String title;//信息标题
    private String text;//信息内容
    private String address;
    public AddressBean(double longitude, double latitude, String title, String text, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.text = text;
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAddress() {
        return address;
    }
}
