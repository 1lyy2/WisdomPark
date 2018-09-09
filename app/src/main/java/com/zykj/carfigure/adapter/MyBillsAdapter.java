package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.Bills;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2016:40
 * desc   :
 * version: 1.0
 */
public class MyBillsAdapter extends BaseRecylerAdapter {
    private int type;
    public MyBillsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyBillsHolder(LayoutInflater.from(mContext).inflate(R.layout.mybills_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyBillsHolder myBillsHolder = (MyBillsHolder) holder;
        Bills bills = (Bills) mList.get(position);
        if(bills==null) return;
        myBillsHolder.tvBillCarnumber.setText(bills.getCarNumber());
        myBillsHolder.tvBillAddress.setText(bills.getAddress());
        myBillsHolder.tvBillTime.setText(bills.getConsumeTime());
        if(getType()==1){
            //充值
            myBillsHolder.tvBillAddress.setVisibility(View.INVISIBLE);
            myBillsHolder.tvBillConsume.setText("+"+bills.getConsumeMoney()+"");

        }else {
            myBillsHolder.tvBillConsume.setText("-"+bills.getConsumeMoney()+"");
            myBillsHolder.tvBillAddress.setVisibility(View.VISIBLE);
        }

        myBillsHolder.tvRemain.setText(bills.getRemainMoney()+"");
    }

    class MyBillsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bill_carnumber)
        TextView tvBillCarnumber;
        @BindView(R.id.tv_bill_address)
        TextView tvBillAddress;
        @BindView(R.id.tv_bill_time)
        TextView tvBillTime;
        @BindView(R.id.tv_bill_consume)
        TextView tvBillConsume;
        @BindView(R.id.tv_remain)
        TextView tvRemain;
        public MyBillsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
