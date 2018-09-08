package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.ParkRecordAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.ParkingRecord;
import com.zykj.carfigure.mvp.presenter.ParkingRecordPresenter;
import com.zykj.carfigure.mvp.view.IParkingRecordView;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

//停车记录
public class ParkRecordActivity extends UserBaseActivity implements IParkingRecordView {

    @BindView(R.id.common_right_text)
    TextView commonRightText;
    @BindView(R.id.record_recyclerView)
    EmptyRecyclerView recordRecyclerView;
    @BindView(R.id.record_refresh)
    SwipeRefreshLayout recordRefresh;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.text_view)
    TextView empty_text;
    private ParkRecordAdapter parkRecordAdapter;
    private ParkingRecordPresenter parkingRecordPresenter;

    @Override
    public void onCreatePresenter() {
        parkingRecordPresenter = new ParkingRecordPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_park_record;
    }

    @Override
    protected void initView() {
        init();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.park_record_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.common_right_text)
    public void onViewClicked() {

    }

    private void init() {
        enableSupportActionBar();
        empty_text.setText("您当前没有停车记录");
        commonRightText.setTextColor(getResources().getColor(R.color.common_title_text));
        commonRightText.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordRecyclerView.setLayoutManager(linearLayoutManager);
        recordRecyclerView.setEmptyView(emptyView);
        parkRecordAdapter = new ParkRecordAdapter(this);
        recordRecyclerView.setAdapter(parkRecordAdapter);
        parkingRecordPresenter.getParkingRecordList(Constants.user_id);
    }

    @Override
    public void getParkingRecordListSuccess(ParkingRecord parkingRecord) {

    }

    @Override
    public void getParkingRecordListFailed() {

    }
}
