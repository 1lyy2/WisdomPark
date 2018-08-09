package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/911:47
 * desc   : 我的车辆
 * version: 1.0
 */
public class MyCarAdapter extends BaseRecylerAdapter {
    private Context context;

    public MyCarAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCarHolder(LayoutInflater.from(context).inflate(R.layout.mycar_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class MyCarHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mycar_number)
        TextView tvMycarNumber;
        @BindView(R.id.btn_del_mycar)
        Button btnDelMycar;
        @BindView(R.id.mycar_cardview)
        CardView mycarCardview;

        public MyCarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
