package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.WaitPayAdapter;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.RechargeType;
import com.zykj.carfigure.entity.WaitPay;
import com.zykj.carfigure.widget.CommonItemDecoration;
import com.zykj.carfigure.widget.EmptyRecyclerView;
import com.zykj.carfigure.widget.popup.PayMoneyPopup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//代缴车费
public class WaitPayFareActivity extends UserBaseActivity implements WaitPayAdapter.ToPayListener ,PayMoneyPopup.ToPayWaitMoneyListener {

    @BindView(R.id.waiypay_recycle)
    EmptyRecyclerView waiypayRecycle;
    @BindView(R.id.waitypay_swrefresh)
    SwipeRefreshLayout waitypaySwrefresh;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.empty_view_waitpay)
    View emptyLin;
    private WaitPayAdapter waitPayAdapter;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_pay_fare;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        init();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.pay_fare_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init() {
        waiypayRecycle.setEmptyView(emptyLin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        waiypayRecycle.setLayoutManager(linearLayoutManager);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(this, 1, getResources().getColor(R.color.common_spite_color));
        waiypayRecycle.addItemDecoration(commonItemDecoration);
        waitPayAdapter = new WaitPayAdapter(this, this);
        waiypayRecycle.setAdapter(waitPayAdapter);
        waitPayAdapter.setList(initData());
    }

    private List<WaitPay> initData() {
        List<WaitPay> list = new ArrayList<>();
        WaitPay rechargeType = new WaitPay("桂A88888", "江南万达", "2018.9.16 14:00", 8, "3小时15分");
        list.add(rechargeType);
        WaitPay rechargeType2 = new WaitPay("桂A66666", "青秀万达", "2018.9.16 14:00", 18, "5小时15分");
        list.add(rechargeType2);
        WaitPay rechargeType3 = new WaitPay("桂A868686", "斗鱼万达", "2015.9.16 14:00", 28, "6小时15分");
        list.add(rechargeType3);
        return list;
    }

    //去缴费
    @Override
    public void toPay(WaitPay waitPay) {
        if (waitPay == null) return;
        PayMoneyPopup payMoneyPopup = new PayMoneyPopup(this, waitPay,this);
        payMoneyPopup.showAtLocation(waiypayRecycle, Gravity.BOTTOM, 0, 0);
    }

    public static List<RechargeType> initPayTypeData() {
        List<RechargeType> list = new ArrayList<>();
        RechargeType rechargeType = new RechargeType(R.mipmap.ic_launcher_round, "支付宝", 1);
        list.add(rechargeType);
        RechargeType rechargeType2 = new RechargeType(R.mipmap.ic_launcher_round, "微信", 2);
        list.add(rechargeType2);
        RechargeType rechargeType3 = new RechargeType(R.mipmap.ic_launcher_round, "银联在线", 3);
        list.add(rechargeType3);
        return list;
    }

    @Override
    public void toPayWaitMoney(int type, double money) {
        showLoadingView(null,null);
    }
}
