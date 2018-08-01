package com.zykj.carfigure.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zykj.carfigure.R;

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
        options.placeholder(placeholder)
                .error(error);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadNoOptions(Context context,
                            String url,
                            ImageView imageView) {
        RequestOptions options =new RequestOptions();
        options.placeholder(placeholder)
                .error(error);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
