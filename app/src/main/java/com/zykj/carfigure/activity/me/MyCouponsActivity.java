package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCouponsPageAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.fragment.CouponsFragment;

import java.util.ArrayList;

import butterknife.BindView;

//优惠劵
public class MyCouponsActivity extends BaseActivity {

    @BindView(R.id.common_right_text)
    TextView commonRightText;
    @BindView(R.id.tab_coupon)
    SmartTabLayout mSlidingTabLayout;
    @BindView(R.id.vp_coupon)
    ViewPager viewPager;
    ArrayList<String> titleDatas = new ArrayList<>();
    ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_coupons;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setText("使用规则");
        commonRightText.setTextColor(getResources().getColor(R.color.commom_text_color));
        init_view();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.coupons_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init_view() {
        titleDatas.add("全部");
        titleDatas.add("未使用");
        titleDatas.add("已使用");
        titleDatas.add("已过期");
        CouponsFragment all = new CouponsFragment();
        all.setType(0);
        CouponsFragment unused = new CouponsFragment();
        unused.setType(1);
        CouponsFragment not_used = new CouponsFragment();
        not_used.setType(2);
        CouponsFragment used = new CouponsFragment();
        used.setType(3);
        fragmentList.add(all);
        fragmentList.add(unused);
        fragmentList.add(not_used);
        fragmentList.add(used);

        MyCouponsPageAdapter myViewPageAdapter = new MyCouponsPageAdapter(getSupportFragmentManager(), titleDatas, fragmentList);
        viewPager.setAdapter(myViewPageAdapter);
        mSlidingTabLayout.setViewPager(viewPager);
    }
}
