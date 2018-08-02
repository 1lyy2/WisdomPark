package com.zykj.carfigure.app;

/**
 * 配置常量
 */
public class AppConfig {
    public static final boolean DEBUG = true;// 测试模式，正式模式后改为false,log不在打印以及其他使用线上设置
    public static final int DEFULT_PAGE_MUNBER = 15;   //默认列表数据页数
    public static final String APP_ID = "com.zykj.carfigure";    //APP标识~包名

    public static final String FILE_NAME = "carfigure.u";// 存uuid的文件名
    public static final String DIR = ".carfigure/";

    public static final String WX_APPID = "wx9bd2a2ebf2ee4b30";//微信相关
    public static final String WX_APPSecret = "39fd0d45dcf1b32da2606d0b4b26e587";

    public static final String QQ_APPID = "1107392893";//QQ相关
    public static final String QQ_APPKey = "G5lYz0MBeC4RPDXr";

    public static final String ALIPAY_APPID = "2018080160821976";//支付宝


    public static final int pageCount = DEFULT_PAGE_MUNBER;//一页有多少条记录
    //https://api.xiaomaikeji.com //正式服
    //https://apitest.xiaomaikeji.com//测试服
    private static final String BASE_PATH = "http://47.94.45.120:8084/klq";//域名

    public static final String API_BASE_PATH = BASE_PATH + "/";//API基础路径

    public static final int BADDUMAP = 1;  //百度地图
    public static final int GAODEMAP = 2;  //高德地图
    public static final int TENCENTMAP = 3; //腾讯地图


}
