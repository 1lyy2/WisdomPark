package com.zykj.carfigure.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.carfigure.R;


/**
 * Created by Lich_Cool on 2018\1\27 0027.
 */

public class DataEmptyView extends FrameLayout {

    private ImageView mEmptyImage;
    private TextView mEmptyText;

    public DataEmptyView(Context context) {
        this(context, null);
    }

    public DataEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.view_data_empty, this, true);
        mEmptyImage = (ImageView) emptyView.findViewById(R.id.image_view);
        mEmptyText = (TextView) emptyView.findViewById(R.id.text_view);

        emptyView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LayoutParams layoutParams = (LayoutParams) emptyView.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(layoutParams);
            }
        });

    }

    private int dp2px(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public void setEmptyImage(@DrawableRes int emptyImage) {
        mEmptyImage.setBackgroundResource(emptyImage);
    }

    public void setEmptyText(CharSequence emptyText) {
        mEmptyText.setText(emptyText);
    }

}