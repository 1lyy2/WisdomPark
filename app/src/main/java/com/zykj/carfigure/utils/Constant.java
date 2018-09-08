package com.zykj.carfigure.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.view.WindowManager;

import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.log.Log;

import java.io.File;

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
            if (i == 1) {
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
                FilePath.APK_CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.APP_Packages + Constants.APP_FILE_CACHE;
                // FilePath.IMAGE_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + "/";
                FilePath.GLIDE_CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.APP_Packages + Constants.APP_IMGAGE_CACHE;
            } else {
                FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + Constants.APP_Packages + Constants.APP_FILE_CACHE;
                // FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
                FilePath.GLIDE_CACHE_DIR = context.getExternalCacheDir().getAbsolutePath() + Constants.APP_Packages + Constants.APP_IMGAGE_CACHE;
            }
        } catch (Exception e) {
            FilePath.APK_CACHE_DIR = context.getCacheDir().getAbsolutePath() + Constants.APP_Packages + Constants.APP_FILE_CACHE;
            // FilePath.IMAGE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + "/";
            FilePath.GLIDE_CACHE_DIR = context.getCacheDir().getAbsolutePath() + Constants.APP_Packages + Constants.APP_IMGAGE_CACHE;
        }
        File dir = new File(FilePath.GLIDE_CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dirs = new File(FilePath.APK_CACHE_DIR);
        if (!dirs.exists()) {
            dirs.mkdirs();
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
     * //@see #IMAGE_CACHE_DIR 图片缓存路径
     *
     * @see #APK_CACHE_DIR 客户端更新APK路径
     */
    public static class FilePath {
        //public static String IMAGE_CACHE_DIR;
        public static String APK_CACHE_DIR;
        public static String GLIDE_CACHE_DIR;
    }

    public static class ImageSize {
        public static String LIST_IMAGE_SIZE = "_160x160.jpg"; // 测试
        public static String DETAILS_IMAGE_SIZE = "_280x280.jpg"; // 游客
    }
}
