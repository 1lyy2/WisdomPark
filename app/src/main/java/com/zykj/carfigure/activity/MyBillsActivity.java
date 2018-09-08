package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyBillsAdapter;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.Bills;
import com.zykj.carfigure.widget.CommonItemDecoration;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的账单
 */
public class MyBillsActivity extends UserBaseActivity {

    @BindView(R.id.rel_bills_empty)
    RelativeLayout relBillsEmpty;
    @BindView(R.id.recycler_bills)
    EmptyRecyclerView recyclerBills;
    private MyBillsAdapter  myBillsAdapter;

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
        init();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.my_bills_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private  void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerBills.setLayoutManager(linearLayoutManager);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(this,1,getResources().getColor(R.color.common_spite_color));
        recyclerBills.addItemDecoration(commonItemDecoration);
        myBillsAdapter = new MyBillsAdapter(this);
        recyclerBills.setAdapter(myBillsAdapter);
        myBillsAdapter.setList(initData());
        recyclerBills.setEmptyView(relBillsEmpty);
    }
    private  List<Bills> initData(){
        List<Bills> list = new ArrayList<>();
        list.add(new Bills("桂A88888","青秀万达","2018.9.16 15:35",6,30,1));
        list.add(new Bills("充值","","2018.9.16 15:35",6,36,2));
        return list;
    }

}
