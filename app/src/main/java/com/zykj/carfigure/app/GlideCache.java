package com.zykj.carfigure.app;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.zykj.carfigure.utils.Constant;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1020:09
 * desc   :
 * version: 1.0
 */
@GlideModule
public class GlideCache extends AppGlideModule {
    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath();
    private String appRootPath = null;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        设置缓存大小为20mb
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        //        设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
        if (isSDCardEnable()) {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, Constant.FilePath.GLIDE_CACHE_DIR, memoryCacheSizeBytes));
            sdRootPath = Constant.FilePath.GLIDE_CACHE_DIR;
        } else {

            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, Constant.FilePath.GLIDE_CACHE_DIR, memoryCacheSizeBytes));
        }

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    //    针对V4用户可以提升速度
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    public static boolean isSDCardEnable() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

}
