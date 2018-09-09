package com.zykj.carfigure.http;

import android.os.Environment;

import com.zykj.carfigure.log.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttp3Utils {
/**
        * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;


    public OkHttp3Utils() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (OkHttp3Utils.class) {
                if (okHttpClient == null) {
                    //判空 为空创建实例
                    // okHttpClient = new OkHttpClient();
/**
 * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，而是通过OkHttpClient.Builder来设置，
 * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
 * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
 */

                    File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
                    int cacheSize = 10 * 1024 * 1024;
                    okHttpClient = new OkHttpClient.Builder()
                            .cookieJar(CookieJar.NO_COOKIES)
                            .connectTimeout(5000, TimeUnit.SECONDS)
                            .writeTimeout(2000, TimeUnit.SECONDS)
                            .readTimeout(2000, TimeUnit.SECONDS)
                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                            .addInterceptor(new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    Log.i("zzz", "request====" + request.headers().toString());
                                    okhttp3.Response proceed = chain.proceed(request);
                                    Log.i("zzz", "proceed====" + proceed.headers().toString());
                                    return proceed;
                                }
                            })
                            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                                @Override
                                public void log(String message) {
                                    Log.i("http", message);
                                }
                            }).setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build();
                }
            }

        }

        return okHttpClient;
    }


}