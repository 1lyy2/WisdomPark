package com.zykj.carfigure.entity;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2016:48
 * desc   : 账单
 * version: 1.0
 */
public class Bills {
    private String carNumber;//车牌
    private String address;//消费地址
    private String consumeTime;//消费时间
    private double consumeMoney;//消费的钱
    private double remainMoney;//剩余的钱
    private int type ;//是充值还是消费

    public Bills(String carNumber, String address, String consumeTime, double consumeMoney, double remainMoney,int type) {
        this.carNumber = carNumber;
        this.address = address;
        this.consumeTime = consumeTime;
        this.consumeMoney = consumeMoney;
        this.remainMoney = remainMoney;
        this.type = type;
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

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public double getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(double consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
