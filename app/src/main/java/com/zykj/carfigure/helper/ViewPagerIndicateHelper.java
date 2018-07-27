package com.zykj.carfigure.helper;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zykj.carfigure.R;


/**
 */
public class ViewPagerIndicateHelper {

    public ViewPager.OnPageChangeListener getBaseListener() {
        return baseListener;
    }

    private ViewPager.OnPageChangeListener baseListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (lisntener != null) {
                lisntener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            imageViews[position].setBackgroundResource(selectedResource);
            // 底部小圆点变化
            for (int i = 0; i < imageViews.length; i++) {
                if (position != i) {
                    imageViews[i].setBackgroundResource(normalResource);
                }
            }

            if (lisntener != null) {
                lisntener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (lisntener != null) {
                lisntener.onPageScrollStateChanged(state);
            }
        }
    };

    public ViewPagerIndicateHelper(ViewPager viewPager, ViewGroup group, int normalResource, int selectedResource) {
        this.normalResource = normalResource;
        this.selectedResource = selectedResource;
        this.viewPager = viewPager;
        this.group = group;
        initPageView();
    }


    //region 定义
    ViewPager.OnPageChangeListener lisntener;
    private ImageView[] imageViews;
    private ImageView imageView;
    final int normalResource;
    final int selectedResource;
    ViewPager viewPager;
    ViewGroup group;
    //endregion

    public void setLisntener(ViewPager.OnPageChangeListener lisntener) {
        this.lisntener = lisntener;
    }

    public ViewPagerIndicateHelper setViewPagerIndicate() {

        viewPager.setOnPageChangeListener(baseListener);
        return this;
    }

    private void initPageView() {
        group.removeAllViews();
        int count = viewPager.getAdapter().getCount();
        imageViews = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(viewPager.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(viewPager.getContext().getResources().getDimensionPixelOffset(R.dimen._20px), viewPager.getContext().getResources().getDimensionPixelOffset(R.dimen._20px));
            lp.setMargins(7, 0, 7, 0);
            imageView.setLayoutParams(lp);

            imageView.setPadding(viewPager.getContext().getResources().getDimensionPixelOffset(R.dimen._20px), 0, viewPager.getContext().getResources().getDimensionPixelOffset(R.dimen._20px), 0);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i].setBackgroundResource(selectedResource);
            } else {
                imageViews[i].setBackgroundResource(normalResource);
            }
            group.addView(imageView);
        }
    }

}
