package com.zykj.carfigure.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyBillsAdapter;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.Bills;
import com.zykj.carfigure.mvp.view.IBillsView;
import com.zykj.carfigure.widget.CommonItemDecoration;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BillsFragment extends BaseFragment implements IBillsView {

    @BindView(R.id.recycle_bills)
    EmptyRecyclerView recycleBills;
    @BindView(R.id.swrefresh_bills)
    SwipeRefreshLayout swrefreshBills;
    private MyBillsAdapter myBillsAdapter;
    private int type=-1;//账单类型，0表示已经缴费，-1表示代缴费，1表示充值记录

    @Override
    protected void initView(View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleBills.setLayoutManager(linearLayoutManager);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(getContext(),1,getResources().getColor(R.color.common_spite_color));
        recycleBills.addItemDecoration(commonItemDecoration);
        myBillsAdapter = new MyBillsAdapter(getContext());
        recycleBills.setAdapter(myBillsAdapter);
        myBillsAdapter.setList(initData());
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_bills;
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

    private List<Bills> initData() {
        List<Bills> list = new ArrayList<>();
        list.add(new Bills("桂A88888", "青秀万达", "2018.9.16 15:35", 6, 30, 1));
        list.add(new Bills("充值", "", "2018.9.16 15:35", 6, 36, 2));
        return list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void getUserBillsSuccess(Bills bills) {

    }

    @Override
    public void getUserBillsFailed() {

    }
}
