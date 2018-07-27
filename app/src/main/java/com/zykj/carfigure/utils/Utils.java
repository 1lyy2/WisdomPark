package com.zykj.carfigure.utils;

import android.content.Context;

public class Utils {
    //dip和px转换
    public  static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
