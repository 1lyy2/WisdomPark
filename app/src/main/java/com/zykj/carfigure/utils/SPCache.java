package com.zykj.carfigure.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.zykj.carfigure.app.AppConfig;

/**
 * 缓存信息
 */
public class SPCache {
	
	/**
	 * 缓存信息
	 * @param context 上下文
	 * @param cacheName	存入名字
	 * @param obj	对象
	 */
	public static void saveObject(Context context,String cacheName,Object obj){
		Editor e = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE).edit();
        String json =  new Gson().toJson(obj);
		e.putString(cacheName, json);
		e.commit();
	}
	
	/**
	 * 读取缓存信息
	 * @param <T>
	 * @param context	上下文
	 * @param cacheName	缓存名
	 * @param classes	对象类型
	 */
	public static <T> T getObject(Context context,String cacheName,Class<T> classes){
		return getObject(context,cacheName,classes,null);
	}

    /**
     * 读取缓存信息
     * @param <T>
     * @param context	上下文
     * @param cacheName	缓存名
     * @param classes	对象类型
     * @param defaultValue	默认参数
     */
    public static <T> T getObject(Context context,String cacheName,Class<T> classes,T defaultValue){
        SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
        String jsonString = s.getString(cacheName, "");
        if(!"".equals(jsonString)){
            T o = (T) new Gson().fromJson(jsonString, classes);
            return o;
        }
        return defaultValue;
    }

    /**
     * 读取缓存信息
     * 读取List 等列表要这么写 new TypeToken<ArrayList<GoodsCategory>>(){}.getType()
     * @param <T>
     * @param context	上下文
     * @param cacheName	缓存名
     * @param type	对象类型
     */
    public static <T> T getObject(Context context,String cacheName,Type type){
        return getObject(context,cacheName,type,null);
    }

    /**
     * 读取缓存信息
     * @param <T>
     * @param context	上下文
     * @param cacheName	缓存名
     * @param type	对象类型
     * @param defaultValue	默认参数
     */
    public static <T> T getObject(Context context,String cacheName,Type type,T defaultValue){
        SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
        String jsonString = s.getString(cacheName, "");
        if(!"".equals(jsonString)){
            T o = new Gson().fromJson(jsonString, type);
            return o;
        }
        return defaultValue;
    }


	
	/**
	 * 删除信息
	 * @param context 上下文
	 * @param cacheName 缓存名
	 */
	public static void delObject(Context context,String cacheName){
		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
		s.edit().remove(cacheName).commit();
	}

//
//	//追加环信用户信息
//
//	private static String SHARED_KEY_CURRENTUSER_USERNAME = "KEY_CURRENTUSER_USERNAME";
//	private static String SHARED_KEY_CURRENTUSER_NICK = "KEY_CURRENTUSER_NICK";
//	private static String SHARED_KEY_CURRENTUSER_AVATAR = "KEY_CURRENTUSER_AVATAR";
//
//
//	public static void setCurrentUserNick(Context context,String nickname) {
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		Editor e = s.edit();
//		e.putString(SHARED_KEY_CURRENTUSER_NICK, nickname);
//		e.commit();
//	}

//	public static void setCurrentUserAvatar(Context context,String avatar) {
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		Editor e = s.edit();
//		e.putString(SHARED_KEY_CURRENTUSER_AVATAR, avatar);
//		e.commit();
//	}

//	public static String getCurrentUserNick(Context context) {
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		return s.getString(SHARED_KEY_CURRENTUSER_NICK, null);
//	}

//	public static String getCurrentUserAvatar(Context context) {
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		return s.getString(SHARED_KEY_CURRENTUSER_AVATAR, null);
//	}
//
//	public static void setCurrentUserName(Context context,String username){
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		Editor e = s.edit();
//		e.putString(SHARED_KEY_CURRENTUSER_USERNAME, username);
//		e.commit();
//	}

//	public static String getCurrentUsername(Context context){
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		return s.getString(SHARED_KEY_CURRENTUSER_USERNAME, null);
//	}
//
//
//	public static void removeCurrentUserInfo(Context context) {
//		SharedPreferences s = context.getSharedPreferences(AppConfig.APP_ID, Context.MODE_PRIVATE);
//		Editor e = s.edit();
//		e.remove(SHARED_KEY_CURRENTUSER_NICK);
//		e.remove(SHARED_KEY_CURRENTUSER_AVATAR);
//		e.commit();
//	}

}
