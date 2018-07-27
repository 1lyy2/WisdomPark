package com.zykj.carfigure.entity;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;

//街道
public class Street implements Serializable {
    //街道ID
    public String id;
    //街道经纬度
    public LatLng mLatLng;
    //街道名称
    public String streetName;
    //街道总车位
    public int totalPark;
    //街道空余车位
    public int freePark;


    public Street() {
    }
    public Street(LatLng latLng,String streetName){
        this.mLatLng= latLng;
        this.streetName= streetName;
    }


}
