package com.zykj.carfigure.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.zykj.carfigure.R;
import com.zykj.carfigure.app.GlideApp;

import java.io.File;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3117:40
 * desc   : Glide的工具类
 * version: 1.0
 */
public class GlideUtil {
    public static int placeholder = R.mipmap.ic_launcher;
    public static int error = R.mipmap.ic_launcher_round;

    /**
     * 自定义可以操作的glide
     *
     * @param context
     * @param url
     * @param imageView
     * @param options
     */
    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        if (imageView == null) return;
        options.placeholder(placeholder)
                .error(error);
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadNoOptions(Context context,
                                     String url,
                                     ImageView imageView) {
        if (imageView == null) return;
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder)
                .error(error);
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 清除内存
     * 主线程
     * @param context
     */
    public static void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除缓存
     *  要在子线程执行
     * @param context
     */
    public static void clearFileCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(context).clearDiskCache();
            }
        }).start();
    }
    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long 单位为M
     * @throws Exception
     */
    public static float getFolderSize(File file) {
        float size = 0;
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return 0.00f;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size / 1048576;
    }
    public static boolean cleanLocalCache() {
        File dir = new File(Constant.FilePath.GLIDE_CACHE_DIR);
        boolean b = deleteDir(dir);
        File file = new File(Constant.FilePath.GLIDE_CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return b;
    }
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
