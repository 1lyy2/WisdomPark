package com.zykj.carfigure.activity.login;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.utils.Constant;
import com.zykj.carfigure.utils.SPCache;

public class SplashActivity extends BaseActivity {
    private MyCountDownTimer mc;
    private TextView         tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestPermissions(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tv = (TextView) findViewById(R.id.tv_splshtime);
        mc = new MyCountDownTimer(3000, 1000);
        mc.start();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                enterMainHandler();
            }
        }, 3000);
    }

    @Override
    protected String getActivityName() {
        return "";
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private Handler handler=new Handler();
    /**
     * 继承 CountDownTimer 防范
     *
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tv.setText("正在跳转");
        }

        public void onTick(long millisUntilFinished) {
            tv.setText("倒计时(" + millisUntilFinished / 1000 + ")");
        }

    }
    /**
     * 进入首页逻辑
     */
    private void enterMainHandler(){
        if (!SPCache.getObject(this, Constant.Appinfo.APP_VERSION, Boolean.class, false)) {
            launchActivity(GuildActivity.class);
            SPCache.saveObject(this, Constant.Appinfo.APP_VERSION, true);
        }else{
            launchActivity(MainActivity.class);
            //launchActivity(GuildActivity.class);
        }
        SplashActivity.this.finish();
    }

}
