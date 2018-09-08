package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2018:00
 * desc   : 代缴费
 * version: 1.0
 */
public class WaitPay implements Serializable{
    private String carNumber;//车牌
    private String address;//消费地址
    private String parkTime;//停车时间
    private double payMoney;//代缴费
    private String parkCountTime;//停车总时长

    public WaitPay(String carNumber, String address, String parkTime, double payMoney, String parkCountTime) {
        this.carNumber = carNumber;
        this.address = address;
        this.parkTime = parkTime;
        this.payMoney = payMoney;
        this.parkCountTime = parkCountTime;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParkTime() {
        return parkTime;
    }

    public void setParkTime(String parkTime) {
        this.parkTime = parkTime;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getParkCountTime() {
        return parkCountTime;
    }

    public void setParkCountTime(String parkCountTime) {
        this.parkCountTime = parkCountTime;
    }
}
