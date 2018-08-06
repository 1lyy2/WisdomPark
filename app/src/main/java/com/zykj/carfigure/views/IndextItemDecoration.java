package com.zykj.carfigure.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.carfigure.utils.Utils;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3114:08
 * desc   : 首页悬浮
 * version: 1.0
 */
public class IndextItemDecoration extends RecyclerView.ItemDecoration {
    //宽度
    private int mDividerHeight;
    //画笔
    private Paint mPaint;
    //画笔颜色
    private int color;
    //context
    private Context context;

    public IndextItemDecoration(Context mContext, float mDividerHeight, int color) {
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

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (position == -1) return;//在搜索到没有的索引的时候position可能等于-1，所以在这里判断一下

    }
}
