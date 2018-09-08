package com.zykj.carfigure.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.FreeParkingAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.CarPark;
import com.zykj.carfigure.mvp.presenter.CarParkPresenter;
import com.zykj.carfigure.mvp.view.ICarParkView;

import java.util.List;

import butterknife.BindView;

/**
 * 空车位列表
 */
public class FreeParkActivity extends BaseActivity implements FreeParkingAdapter.OrderListener, ICarParkView {

    @BindView(R.id.common_right_img)
    ImageView commonRightImg;
    @BindView(R.id.freepark_recycle)
    RecyclerView freeparkRecycle;
    @BindView(R.id.freepark_swfresh)
    SwipeRefreshLayout freeparkSwfresh;
    private FreeParkingAdapter freeParkingAdapter;
    private int streetId;
    private CarParkPresenter carParkPresenter;

    @Override
    public void onCreatePresenter() {
        carParkPresenter = new CarParkPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_free_park;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        commonRightImg.setVisibility(View.VISIBLE);
        commonRightImg.setBackgroundResource(R.mipmap.ic_launcher_round);
        init();
        Intent intent = getIntent();
        streetId = intent.getIntExtra("streetId", 0);
        showLoadingView(null, null);
        carParkPresenter.getFreeCarParkList(streetId,0);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.free_park_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        freeparkRecycle.setLayoutManager(linearLayoutManager);
        freeParkingAdapter = new FreeParkingAdapter(this, this);
        freeparkRecycle.setAdapter(freeParkingAdapter);
    }

    @Override
    public void order(CarPark.CarParkDetail carPark) {
        Intent intent = new Intent(FreeParkActivity.this, ReserveParkingActivity.class);
        intent.putExtra(ReserveParkingActivity.ORDERADDRESS, carPark);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void getCarParkDetailSuccess(CarPark carPark) {

    }

    @Override
    public void getCarParkDetailFailed() {

    }

    @Override
    public void getFreeCarParkListSuccess(CarPark carPark) {
        hideLoadingView();
        if (carPark == null) return;
        if (carPark.getStatus() == 200) {
            List<CarPark.CarParkDetail> data = carPark.getData();
            if (freeParkingAdapter != null) {
                freeParkingAdapter.setList(data);
            }
        }

    }

    @Override
    public void getFreeCarParkListFailed() {
        hideLoadingView();
        showToastMsgShort("获取空闲车位列表失败");
    }
}
