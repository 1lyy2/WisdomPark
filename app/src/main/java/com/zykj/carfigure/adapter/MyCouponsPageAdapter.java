package com.zykj.carfigure.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.zykj.carfigure.base.BaseFragment;

import java.util.ArrayList;

/**
 * $desc
 * 优惠券的适配器 viewpage
 * @author lyy
 * @time 2017/10/23/023  8:53
 */

public class MyCouponsPageAdapter  extends FragmentPagerAdapter {
    private ArrayList<String> titleList;
    private ArrayList<BaseFragment> fragmentList;
    public MyCouponsPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<BaseFragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }
    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        try {
            ((ViewPager) container).removeView((View) object);
        } catch (Exception e) {
        }
    }
}
