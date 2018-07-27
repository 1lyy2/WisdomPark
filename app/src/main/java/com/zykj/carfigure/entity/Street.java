package com.zykj.carfigure.entity;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;

//街道
public class Street implements Serializable {
    //街道ID
    private int  id;
    //街道经纬度
    private LatLng mLatLng;
    //街道名称
    private String streetName;
    //街道总车位
    private int totalPark;
    //街道空余车位


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getTotalPark() {
        return totalPark;
    }

    public void setTotalPark(int totalPark) {
        this.totalPark = totalPark;
    }

    public int getFreePark() {
        return freePark;
    }

    public void setFreePark(int freePark) {
        this.freePark = freePark;
    }

    public double getStreetLength() {
        return streetLength;
    }

    public void setStreetLength(double streetLength) {
        this.streetLength = streetLength;
    }

    private int freePark;
    //街道长度
    private double streetLength;


    public Street() {
    }

    public Street(LatLng latLng, String streetName) {
        this.mLatLng = latLng;
        this.streetName = streetName;
    }

    public Street(LatLng mLatLng, String streetName, int totalPark, int freePark, double streetLength) {
        this.mLatLng = mLatLng;
        this.streetName = streetName;
        this.totalPark = totalPark;
        this.freePark = freePark;
        this.streetLength = streetLength;
    }
}
