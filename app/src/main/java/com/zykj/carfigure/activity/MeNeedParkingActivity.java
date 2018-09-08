package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCouponsPageAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.fragment.AutoParkingFragment;
import com.zykj.carfigure.fragment.EditParkingFragment;

import java.util.ArrayList;

import butterknife.BindView;

//直接停车
public class MeNeedParkingActivity extends UserBaseActivity {

    @BindView(R.id.my_parking_smart)
    SmartTabLayout myParkingSmart;
    @BindView(R.id.parking_coupon)
    ViewPager parkingCoupon;
    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_direct_parking;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        initTitle();
        init_view();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.direct_parking_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void initTitle() {
        titleDatas.add("自动匹配");
        titleDatas.add("手动输入");
    }

    private void init_view() {
        AutoParkingFragment autoParkingFragment = new AutoParkingFragment();
        fragmentList.add(autoParkingFragment);
        EditParkingFragment editParkingFragment = new EditParkingFragment();
        fragmentList.add(editParkingFragment);
        MyCouponsPageAdapter myViewPageAdapter = new MyCouponsPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        parkingCoupon.setAdapter(myViewPageAdapter);
        myParkingSmart.setViewPager(parkingCoupon);

    }
    public void setPageInputNumber(){
        parkingCoupon.setCurrentItem(1);
    }
}
