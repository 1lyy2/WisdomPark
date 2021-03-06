package com.zykj.carfigure.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.carfigure.utils.Utils;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3114:08
 * desc   : 普通的分割线
 * version: 1.0
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    //宽度
    private int mDividerHeight;
    //画笔
    private Paint mPaint;
    //画笔颜色
    private int color;
    //context
    private Context context;

    public CommonItemDecoration(Context mContext, float mDividerHeight, int color) {
        this.context = mContext;
        this.mDividerHeight = Utils.dip2px(mContext, mDividerHeight);
        this.color = color;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
      /*  int childAdapterPosition = parent.getChildAdapterPosition(view);
        //第一个ItemView不需要在上面绘制分割线
        if (childAdapterPosition != 0) {
            outRect.top = mDividerHeight;
        }*/
        outRect.bottom = mDividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mDividerHeight;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
