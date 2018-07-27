package com.zykj.carfigure.utils;
import java.io.File;
import com.zykj.carfigure.app.AppConfig;
import com.zykj.carfigure.log.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

/**
 * @see FilePath 文件及图片缓存路径：Constant.FilePath
 */
public class Constant {

    private static Context context;

    public static void init(Context c) {
        context = c;
        initFilePath();
        initScreenSize();
        initImageSize();
        initVersion();
    }

    private static void initImageSize() {
        if (Appinfo.width < 720) {
            ImageSize.LIST_IMAGE_SIZE = "_160x160.jpg";
            ImageSize.DETAILS_IMAGE_SIZE = "_280x280.jpg";
        } else if (Appinfo.width >= 720 && Appinfo.width < 1080) {
            ImageSize.LIST_IMAGE_SIZE = "_280x280.jpg";
            ImageSize.DETAILS_IMAGE_SIZE = "";
        } else if (Appinfo.width >= 1080) {
            ImageSize.LIST_IMAGE_SIZE = "";
            ImageSize.DETAILS_IMAGE_SIZE = "";
        }
    }

    private static void initVersion() {
        /* 版本号 */
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {

        }
        if (pi != null) {
            Appinfo.INTERFACE_VERSION = String.valueOf(Integer.valueOf(getVersion(pi.versionName)));
            Appinfo.APP_VERSION = pi.versionName;
            Log.d("Appinfo", " Appinfo.INTERFACE_VERSION=" + Appinfo.INTERFACE_VERSION + "/Appinfo.APP_VERSION =" + Appinfo.APP_VERSION);
        } else {
            Appinfo.APP_VERSION = "1.0.0";
        }
    }

    public static String getVersion(String s) {
        //把版本号 x.x.x 转为 00x00x00x
//        String[] setting = context.getResources().getStringArray(R.array.app_setting);

        String[] strarray = s.split("\\.");   //以.切割成数组
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < strarray.length; i++) {          //获取.的个数循环
            if(i==1){
                for (int l = 0; l < 2; l++) {       //根据需要补0
                    sb.append(0);
                }
            }
            sb.append(strarray[i]);                     //添加版本数字
        }
       return sb.toString();
      //  return "3001102";//此处为奇葩做法          ---记得修改
    }

    private static void initScreenSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Appinfo.width = wm.getDefaultDisplay().getWidth();
        Appinfo.height = wm.getDefaultDisplay().getHeight();
    }

    /**
     * 初始化文件缓存路径
     */
    private static void initFilePath() {

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FilePath.APK_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + "/";
                FilePath.IMAGE_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + "/";
            } else {
                FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
                FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
            }
        } catch (Exception e) {
            FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
            FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
        }
        Log.d("ZHSConstant", FilePath.APK_CACHE_DIR);
    }

    /**
     * 手机基本详情，包括宽度，高度，APP版本号
     */
    public static class Appinfo {
        public static final String INTERFACE_CLIENT_TYPE = "1";//1-安卓，2-ios
        public static int width;
        public static int height;
        public static String APP_VERSION = "1.0.0";
        public static String INTERFACE_VERSION = "1.0.0";

    }
    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        return imei==null?"":imei;
    }
    
    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context){
      TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      @SuppressLint("MissingPermission") String imsi = mTelephonyMgr.getSubscriberId();
      return imsi==null?"":imsi;
    }
    
    public static String getDir() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        } else {
            return FilePath.APK_CACHE_DIR;
        }
        return sdDir.toString() + "/" + AppConfig.DIR;

    }

    /**
     * @see #IMAGE_CACHE_DIR 图片缓存路径
     * @see #APK_CACHE_DIR 客户端更新APK路径
     */
    public static class FilePath {
        public static String IMAGE_CACHE_DIR;
        public static String APK_CACHE_DIR;
    }


    /**
     * 接口返回的共用参数名常量
     */
    public static class AttributeName {
        public static final String STATUS = "status";
        public static final String CODE = "resultCode";
        public static final String MSG = "message";
        public static final String DATA = "data";
        public static final String ROWS = "rows";
        public static final String PAGE = "page";
        public static final String TOTAL = "total";
        public static final String UID = "uid";
        public static final String GID = "gid";
        public static final String OID = "oid";
        public static final String TOTAL_PAGES = "total_pages";
    }

    /**
     * 通用返回code
     */
    public static class ResultCode {
        public static final int SUCCESS = 01;// 操作正确，正常返回
        public static final int USER_LOGIN_TIME_OUT_1201005 = 06; // 用户已经在别的地方登陆，请重新登陆
        public static final int USER_LOGIN_OTHER_1201002 = 06; // token 为空，请登陆后在操作
        public static final int USER_LOGIN_OTHER_06 = 06; // 登陆超时，请重新登陆

    }


    public static class XGTag {
        public static final String PROGRAMMER = "programmer"; // 开发
        public static final String TESTER = "tester"; // 测试
        public static final String TOURIST = "tourist"; // 游客
        public static final String MEMBER = "member"; // 会员
    }

    public static class ImageSize {
        public static String LIST_IMAGE_SIZE = "_160x160.jpg"; // 测试
        public static String DETAILS_IMAGE_SIZE = "_280x280.jpg"; // 游客
    }
}
