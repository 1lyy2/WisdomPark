package com.zykj.carfigure.app;

/**
 * 配置常量
 */
public class AppConfig {
    public static final boolean DEBUG = true;// 测试模式，正式模式后改为false,log不在打印以及其他使用线上设置
    public static final int DEFULT_PAGE_MUNBER = 15;   //默认列表数据页数
    public static final String APP_ID = "com.zykj.carfigure" ;    //APP标识~包名
    public static final String WIFI_KEY = "carfigure:wifi";
    
    public static final String FILE_NAME = "carfigure.u";// 存uuid的文件名
    public static final String DIR = ".carfigure/";
    
    public static final String WX_APPID = "wx9bd2a2ebf2ee4b30";//微信相关
	public static final String WX_APPSecret = "39fd0d45dcf1b32da2606d0b4b26e587";
	
	public static final String QQ_APPID = "1106452627";//QQ相关
	public static final String QQ_APPKey = "TAiQhwUkjwuXUfTt";
	
	
    public static final int pageCount = DEFULT_PAGE_MUNBER;//一页有多少条记录

    public static final String APK_UPDATE_URL = "apk_update_url";
    public static final String SHARE_REGISTER_TITLE = "我0元购买了超值的宝贝，快来和我一起领哟！";
    public static final String SHARE_REGISTER_CONTENT = "康绿全送大礼，发发朋友圈就能领取豪华大礼。";
    //https://api.xiaomaikeji.com //正式服
    //https://apitest.xiaomaikeji.com//测试服
    private static final String BASE_PATH = "http://47.94.45.120:8084/klq";//域名

    public static final String API_BASE_PATH = BASE_PATH + "/";//API基础路径

    
}