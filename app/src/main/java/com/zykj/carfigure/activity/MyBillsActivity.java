package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCouponsPageAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.fragment.BillsFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的账单
 */
public class MyBillsActivity extends UserBaseActivity {

    @BindView(R.id.tab_bills)
    SmartTabLayout tabBills;
    @BindView(R.id.vp_bills)
    ViewPager vpBills;
    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_bills;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        init_view();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.my_bills_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init_view() {
        //0表示已经缴费，-1表示代缴费，1表示充值记录
        titleDatas.add("待缴费");
        titleDatas.add("已缴费");
        titleDatas.add("充值");
        BillsFragment waitPay = new BillsFragment();
        waitPay.setType(-1);
        BillsFragment payed = new BillsFragment();
        payed.setType(0);
        BillsFragment charge = new BillsFragment();
        charge.setType(1);
        fragmentList.add(waitPay);
        fragmentList.add(payed);
        fragmentList.add(charge);

        MyCouponsPageAdapter myViewPageAdapter = new MyCouponsPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        vpBills.setAdapter(myViewPageAdapter);
        tabBills.setViewPager(vpBills);
    }
}
