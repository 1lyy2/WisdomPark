package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3016:59
 * desc   : 反向寻车
 * version: 1.0
 */
public class FindMyCar implements Serializable {
    /**经度 **/
    private double longitude;
    /**纬度**/
    private double  latitude;
    /**街道名字**/
    private String streetName;
    /*泊车号码*/
    private String carParkNumber;

    public FindMyCar( double latitude,double longitude, String streetName, String carParkNumber) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.streetName = streetName;
        this.carParkNumber = carParkNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCarParkNumber() {
        return carParkNumber;
    }

    public void setCarParkNumber(String carParkNumber) {
        this.carParkNumber = carParkNumber;
    }
}
