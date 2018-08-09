package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/917:07
 * desc   : 停车记录适配器
 * version: 1.0
 */
public class ParkRecordAdapter extends BaseRecylerAdapter {
    public ParkRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParkRecordHolder(LayoutInflater.from(mContext).inflate(R.layout.parkrecord_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class ParkRecordHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_record_money)
        TextView tvRecordMoney;
        @BindView(R.id.tv_record_time)
        TextView tvRecordTime;
        @BindView(R.id.tv_car_number)
        TextView tvCarNumber;
        @BindView(R.id.tv_park_location)
        TextView tvParkLocation;
        @BindView(R.id.tv_park_time)
        TextView tvParkTime;
        public ParkRecordHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
