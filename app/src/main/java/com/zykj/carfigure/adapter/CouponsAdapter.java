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
 * date   : 2018/8/99:42
 * desc   : 优惠劵适配器
 * version: 1.0
 */
public class CouponsAdapter extends BaseRecylerAdapter {
    private int type;
    private Context context;

    public CouponsAdapter(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CouponsHolder(LayoutInflater.from(context).inflate(R.layout.coupons_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class CouponsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_coupons_money)
        TextView tvCouponsMoney;
        @BindView(R.id.tv_user_condition)
        TextView tvUserCondition;
        @BindView(R.id.tv_user_type)
        TextView tvUserType;
        @BindView(R.id.tv_user_date)
        TextView tvUserDate;
        @BindView(R.id.tv_user_now)
        TextView tvUserNow;
        @BindView(R.id.tv_used)
        TextView tvUsed;
        public CouponsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
