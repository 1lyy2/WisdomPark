package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCarAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.views.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

//我的车辆
public class MyCarActivity extends BaseActivity {

    @BindView(R.id.myCarRecyclerView)
    EmptyRecyclerView myCarRecyclerView;
    @BindView(R.id.btn_bindcar)
    Button btnBindcar;
    @BindView(R.id.lin_empty_macar)
    LinearLayout linEmptyMacar;
    @BindView(R.id.common_right_text)
    TextView commonRightText;
    private MyCarAdapter myCarAdapter;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        init();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.my_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init() {
        commonRightText.setText("添加车辆");
        commonRightText.setVisibility(View.VISIBLE);
        commonRightText.setTextColor(getResources().getColor(R.color.common_title_text));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myCarRecyclerView.setLayoutManager(linearLayoutManager);
        myCarRecyclerView.setEmptyView(linEmptyMacar);
        myCarAdapter = new MyCarAdapter(this);
        myCarRecyclerView.setAdapter(myCarAdapter);

    }

    @OnClick({R.id.common_right_text, R.id.btn_bindcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_right_text:
                //添加车辆
            case R.id.btn_bindcar:
                //绑定车辆
                launchActivity(AddCarActivity.class);
                break;
        }
    }
}
