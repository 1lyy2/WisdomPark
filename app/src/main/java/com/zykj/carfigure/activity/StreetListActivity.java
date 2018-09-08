package com.zykj.carfigure.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.StreetListAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.mvp.presenter.StreetPresenter;
import com.zykj.carfigure.mvp.view.IStreetView;
import com.zykj.carfigure.widget.CommonItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * 街道列表
 */
public class StreetListActivity extends BaseActivity implements BaseRecylerAdapter.OnItemClickListener, IStreetView {
    @BindView(R.id.recycleview_street)
    RecyclerView recycleviewStreet;
    private StreetListAdapter streetListAdapter;
    private List<Street.StreetDetail> streetList;
    private double street_latitude;
    private double street_longitude;
    private StreetPresenter streetPresenter;

    @Override
    public void onCreatePresenter() {
        streetPresenter = new StreetPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_street_list;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleviewStreet.setLayoutManager(linearLayoutManager);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(this, 1, getResources().getColor(R.color.common_spite_color));
        recycleviewStreet.addItemDecoration(commonItemDecoration);
        streetListAdapter = new StreetListAdapter(this);
        streetListAdapter.setOnItemClickListener(this);
        recycleviewStreet.setAdapter(streetListAdapter);
        Intent intent = getIntent();
        street_latitude = intent.getDoubleExtra("street_latitude", 0);
        street_longitude = intent.getDoubleExtra("street_longitude", 0);
        showLoadingView(null, null);
        streetPresenter.getStreetList(street_latitude, street_longitude, Constants.range);
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.string_street_list);
    }

    @Override
    protected Context getContext() {
        return this;
    }


    @Override
    public void onItemClick(View view, Object item, int position) {
        Street.StreetDetail streetDetail = (Street.StreetDetail) item;
        if(streetDetail==null) return;
        int id = streetDetail.getId();
        Intent intent = new Intent(StreetListActivity.this,FreeParkActivity.class);
        intent.putExtra("streetId",id);
        startActivity(intent);
    }

    @Override
    public void getStreetListSuccess(Street street) {
        hideLoadingView();
        if (street == null) return;
        if (street.getStatus() == 200) {
            List<Street.StreetDetail> data = street.getData();
            if (streetListAdapter != null) {
                streetListAdapter.setList(data);
            }
        }
    }

    @Override
    public void getStreetListFailed() {
        hideLoadingView();
        showToastMsgShort("获取街道列表失败");
    }
}
