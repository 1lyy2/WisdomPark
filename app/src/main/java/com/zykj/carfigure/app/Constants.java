package com.zykj.carfigure.app;

import com.amap.api.maps.model.LatLng;
import com.zykj.carfigure.entity.User;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1015:07
 * desc   :
 * version: 1.0
 */
public class Constants {
    //app的文件夹
    public static String APP_Packages = "/Carfigure";
    public static String APP_IMGAGE_CACHE = "/imagecache/";
    public static String APP_FILE_CACHE = "/filecache/";
    public static String DEFAULT_CITY = "南宁";
    public static final String EXTRA_TIP = "ExtraTip";
    public static final String KEY_WORDS_NAME = "KeyWord";
    public static double Longitude = 0; //经度
    public static double Latitude = 0;//纬度
    public static LatLng latLng = null;
    public static String username = "";//用户名
    public static int user_id = 0;//用户iD
    public static User user;//记录user数据
    public static int range = 1;//搜索街道的范围
    public static String CacheBanner = "cachebanner.txt";//缓存banner
    public static String CacheFunction = "cachefunction.txt";//缓存Function
    public static float getZoomB = 16f; //地图缩放的
    public static int   SELECTCARNUMBERCODE = 4598;//车牌选择
    public static String car_license_plate="";//车牌号，作用于预约
    //设备号
    public static String devId="356566078065125";
    //数据库名称
    public static String DB_NAME="carfigure_db";
}
