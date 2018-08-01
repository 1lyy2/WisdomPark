package com.zykj.carfigure.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.StreetListAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.views.CommonItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * 街道列表
 */
@BindEventBus
public class StreetListActivity extends BaseActivity implements BaseRecylerAdapter.OnItemClickListener {
    @BindView(R.id.recycleview_street)
    RecyclerView recycleviewStreet;
    private StreetListAdapter streetListAdapter;
    private List<Street> streetList;

    @Override
    public void onCreatePresenter() {
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
        CommonItemDecoration commonItemDecoration =new CommonItemDecoration(this,1,getResources().getColor(R.color.common_spite_color));
        recycleviewStreet.addItemDecoration(commonItemDecoration);
        streetListAdapter = new StreetListAdapter(this);
        streetListAdapter.setOnItemClickListener(this);
        recycleviewStreet.setAdapter(streetListAdapter);
        if (streetList != null) {
            streetListAdapter.setList(streetList);
        }
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.string_street_list);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void handleEvent(Event<Object> event) {
        int code = event.getCode();
        switch (code) {
            case EventCode.STREETDATACHANGE:
                List<Street> list = (List<Street>) event.getData();
                streetList = list;
                if (streetListAdapter != null) {
                    streetListAdapter.setList(list);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
          launchActivity(FreeParkActivity.class);
    }
}
