package com.zykj.carfigure.utils;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastManager {
    public static final String MSG_ERROR = "系统错误,请稍候重试";

    private static Toast mToast;
    private static Handler mhandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
        	mToast.cancel();
        };
    };
    /**
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, final String text, int duration) {
        mhandler.removeCallbacks(r);
        if (null != mToast) {
        	mhandler.post(new Runnable() {
				@Override
				public void run() {
					 mToast.setText(text);
				}
			});
        } else {
            mToast = Toast.makeText(context, text, duration);
        }
        mhandler.postDelayed(r, 5000);
        mToast.show();
    }

    /**
     * @param context
     * @param text
     */
    public static void showShortToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param text
     */
    public static void showLongToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);

    }

    public static void showToast(Context context, int strId, int duration) {
        showToast(context, context.getString(strId), duration);
    }

    /**
     * 重新启用，setCanShowMsg
     */
    public static void hideMsg() {
        if (null != mToast) {
            mToast.cancel();
            mhandler.removeCallbacks(r);
            mToast = null;
        }
    }


}
