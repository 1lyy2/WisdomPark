package com.zykj.carfigure.app;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zykj.carfigure.crash.CrashHandler;
import com.zykj.carfigure.utils.ToastManager;
import java.util.ArrayList;

//自定义Application
public class MyApplication extends Application {
    private static final String                   TAG            = MyApplication.class.getSimpleName();
    /**
     * 客户端唯一标识，在首次启动时，从服务器获取
     **/
    public               String                   deviceid       = "";
    private              ArrayList<Activity>      acArrayList    = null;

    public void onCreate() {
        super.onCreate();
        //统计Activity
        acArrayList = new ArrayList<Activity>();
        //手机crash
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

    }
    public  Context getMyApplication(){
        return getApplicationContext();
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        acArrayList.add(activity);
    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        acArrayList.remove(activity);
    }

    /**
     * 移除结束Activity
     */
    public void closeActivity(Activity activity) {
        acArrayList.remove(activity);
        activity.finish();
    }

    public ArrayList<Activity> getAcArrayList() {
        return acArrayList;
    }

    /**
     * 退出应用
     */
    public void exit() {
        try {
            if (acArrayList != null && !acArrayList.isEmpty()) {
                for (int i = acArrayList.size() - 1; i > -1; i--) {//要从最顶层移除掉才能正常退出
                    Activity a = acArrayList.get(i);
                    acArrayList.remove(i);
                    i = acArrayList.size();
                    a.finish();
                }
            }
            ToastManager.hideMsg();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.exit(0);//加了可能导致toast不消失问题 崩溃捕捉里再使用词句结束应用
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 注销
     *
     * @return true:注销成功,false:其他
     */
    public boolean logout() {
        try {
         /*   if (mLoginUser != null) {
                if (!StrUtil.isEmpty(mLoginUser.getSESSION_ID())) {
                    SPCache.delObject(getApplicationContext(), SESSION);
                }
            }
            mLoginUser = new User();
            PersonalFragment.isAutoRefreshUserInfo = true;//个人中心开启自动刷新
            if (EMClient.getInstance().isLoggedInBefore()) {//还在登录则退出
                EMClient.getInstance().logout(true);
            }*/
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 启动并跳转到LoginActivity登录页
     */
    public void gotoLogin() {
       /* Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }

}
