package com.zykj.carfigure.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1711:57
 * desc   :
 * version: 1.0
 */
@Entity
public class AddressBean implements Serializable {
    static final long serialVersionUID = 42L;
    @Id
    private Long id;
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

    @Generated(hash = 860396690)
    public AddressBean(Long id, double longitude, double latitude, String title, String text,
            String address) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.text = text;
        this.address = address;
    }

    @Generated(hash = 30780671)
    public AddressBean() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
