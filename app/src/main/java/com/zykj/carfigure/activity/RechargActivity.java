package com.zykj.carfigure.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.RechargeAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.RechargeType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//快速充值
public class RechargActivity extends UserBaseActivity implements BaseRecylerAdapter.OnItemClickListener ,RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.edt_recharge_money)
    EditText edtRechargeMoney;
    @BindView(R.id.recharge_type_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.rg_recharge)
    RadioGroup rgRecharge;
    private RechargeAdapter rechargeAdapter;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_recharg;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        init();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.quick_rechage_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init() {
        rechargeAdapter = new RechargeAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rechargeAdapter);
        rechargeAdapter.setList(initData());
        rechargeAdapter.setOnItemClickListener(this);
        rgRecharge.setOnCheckedChangeListener(this);

    }

    private List<RechargeType> initData() {
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
    public void onItemClick(View view, Object item, int position) {
        rechargeAdapter.setSelection(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 获取选中的RadioButton的id
        int id = group.getCheckedRadioButtonId();
        // 通过id实例化选中的这个RadioButton
        RadioButton choise = (RadioButton) findViewById(id);
        // 获取这个RadioButton的text内容
        String output = choise.getText().toString();
        edtRechargeMoney.setText(output);
    }
}
