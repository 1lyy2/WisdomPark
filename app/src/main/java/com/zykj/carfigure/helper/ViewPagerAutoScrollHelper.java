package com.zykj.carfigure.helper;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Created by LZ on 2015/1/19.
 * ViewPager自动滚动
 */
public class ViewPagerAutoScrollHelper {
    private ViewPager viewPager;
    private int delay;
    private boolean isCancel = false;
    private ScrollThread thread;
    private int currentCount = 0;
    private ViewPager.OnPageChangeListener listener;
    private boolean isPause = false;

    public ViewPager.OnPageChangeListener getListener() {
        return listener;
    }

    public ViewPagerAutoScrollHelper(int delay, ViewPager viewPager) {
        this.delay = delay;
        this.viewPager = viewPager;
        listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentCount = 0;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    public void startScroll() {
        if (thread != null) {
            thread.cancel();
        }
        thread = new ScrollThread();
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isCancel) {
                int index = viewPager.getCurrentItem();
                if (index < viewPager.getAdapter().getCount() - 1) {
                    viewPager.setCurrentItem(index + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        }
    };

    public void pause() {
        isPause = true;
    }

    public void continues() {
        isPause = false;
    }

    class ScrollThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isCancel) {
                try {
                    Thread.sleep(1000);
                    if (!isPause)
                        currentCount++;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (currentCount == delay) {
                    handler.sendEmptyMessage(0);
                    currentCount = 0;
                }
            }
        }

        public void cancel() {
            isCancel = true;
        }
    }

    public void cancel() {
        if (thread != null) {
            thread.cancel();
        }
    }
}
