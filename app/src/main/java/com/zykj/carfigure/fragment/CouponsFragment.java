package com.zykj.carfigure.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.CouponsAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.views.EmptyRecyclerView;

import butterknife.BindView;

public class CouponsFragment extends BaseFragment {
    @BindView(R.id.recycleview_coupons)
    EmptyRecyclerView recycleviewCoupons;
    @BindView(R.id.coupon_refresh)
    SwipeRefreshLayout couponRefresh;
    @BindView(R.id.view_data_empty)
    View emptyView;
    private int type;
    private int currentPage = 1;//页码
    private CouponsAdapter couponsAdapter;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void initView(View rootView) {
        init();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_coupons;
    }

    @Override
    protected String getFragmentName() {
        return "";
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {

    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleviewCoupons.setLayoutManager(linearLayoutManager);
        recycleviewCoupons.setEmptyView(emptyView);
        couponsAdapter = new CouponsAdapter(getActivity(), getType());
        recycleviewCoupons.setAdapter(couponsAdapter);
    }

}
