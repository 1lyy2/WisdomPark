package com.zykj.carfigure.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.zykj.carfigure.activity.login.LoginActivity;
import com.zykj.carfigure.crash.CrashHandler;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.fragment.MeFragment;
import com.zykj.carfigure.greendao.DaoMaster;
import com.zykj.carfigure.greendao.DaoSession;
import com.zykj.carfigure.utils.Constant;
import com.zykj.carfigure.utils.SPCache;
import com.zykj.carfigure.utils.ToastManager;

import java.util.ArrayList;

//自定义Application
public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    /**
     * 客户端唯一标识，在首次启动时，从服务器获取
     **/
    public String deviceid = "";
    private ArrayList<Activity> acArrayList = null;
    public static final String SESSION = "session";
    private User mLoginUser;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    //静态单例
    public static volatile MyApplication instances;

    public void onCreate() {
        super.onCreate();
        instances = this;
        //统计Activity
        acArrayList = new ArrayList<Activity>();
        //手机crash
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Constant.init(getMyApplication());
        setDatabase();

    }

    public Context getMyApplication() {
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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    /**
     * 是否登录
     *
     * @return true:已登录，false:其他
     */
    public boolean isLogined() {
        //return getLoginUser() != null && !StrUtil.isEmpty(getLoginUser().getData().getSESSION_ID());
        return getLoginUser() != null;
    }

    public User getLoginUser() {
        return mLoginUser;
    }

    public void setLoginUser(User user) {
        this.mLoginUser = user;
    /*    if (this.mLoginUser != null) {//没有登录用户是null值
            currentUserName = this.mLoginUser.getHUANXIN_ID() + "";//登陆赋值环信userid
        }*/
        SPCache.saveObject(getApplicationContext(), SESSION, this.mLoginUser);//存user
        MeFragment.isAutoRefreshUserInfo = true;//开启自动刷新
    }

    public static MyApplication getInstances() {
        return instances;
    }


    /**
     * 设置greenDao
     */

    private void setDatabase() {

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。

        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。

        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。

        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。

        mHelper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);

        db = mHelper.getWritableDatabase();

        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。

        mDaoMaster = new DaoMaster(db);

        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession() {

        return mDaoSession;

    }

    public SQLiteDatabase getDb() {

        return db;

    }
}
