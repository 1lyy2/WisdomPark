package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCouponsPageAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.fragment.ReserveFragment;

import java.util.ArrayList;

import butterknife.BindView;

//我的预约
public class MyReserveActivity extends UserBaseActivity {

    @BindView(R.id.my_reserve_smart)
    SmartTabLayout myReserveSmart;
    @BindView(R.id.reserve_coupon)
    ViewPager reserveCoupon;
    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reserve_record;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        initTitle();
        init_view();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.my_reserve_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }
    private  void initTitle(){
        titleDatas.add("已预约");
        titleDatas.add("已取消");
        titleDatas.add("已过期");
    }
    private void init_view(){
        //已预约
        ReserveFragment reserve_suceess= new ReserveFragment();
        reserve_suceess.setType(1);
        //已取消
        ReserveFragment cancel = new ReserveFragment();
        cancel.setType(2);
        //已过期
        ReserveFragment outOfDate = new ReserveFragment();
        outOfDate.setType(3);
        fragmentList.add(reserve_suceess);
        fragmentList.add(cancel);
        fragmentList.add(outOfDate);

        MyCouponsPageAdapter myViewPageAdapter = new MyCouponsPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        reserveCoupon.setAdapter(myViewPageAdapter);
        myReserveSmart.setViewPager(reserveCoupon);

    }
}
