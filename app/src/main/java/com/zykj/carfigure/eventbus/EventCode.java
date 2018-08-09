package com.zykj.carfigure.eventbus;

//Event传入一个泛型T，这里T是具体的事件类，Event是将具体事件与对应业务code进行封装
public class EventCode {
    //定位
    public static final int LOCATION=0x555555;
    //刷新定位
    public static final int REFRESH_LOCATION =0x889898;
    //车位的详情
    public static final int NEARCARPARKDETAIL=0x666666;
    //路线
    public static final int ROUTE =0x11225555;
    //导航
    public static final int NAVIGATION = 0x45515588;
    //高德地图
    public static final int GAODEMAP = 0x1111111;
    //百度地图
    public static final int BAIDUMAP = 0x22222222;
    //地图和街道列表之间的数据交互
    public  static final int STREETDATACHANGE = 0x55555588;

}