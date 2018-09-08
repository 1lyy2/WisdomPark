package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.RechargeType;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2014:27
 * desc   : 充值方式列表的适配器
 * version: 1.0
 */
public class RechargeAdapter extends BaseRecylerAdapter {
    private int selected = -1;

    public RechargeAdapter(Context context) {
        super(context);
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RechargeHolder(LayoutInflater.from(mContext).inflate(R.layout.recharge_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RechargeHolder viewHolder = (RechargeHolder) holder;
        RechargeType type = (RechargeType) mList.get(position);
        if(type==null) return;
        viewHolder.rechargeItemImage.setBackgroundResource(R.mipmap.ic_launcher_round);
        viewHolder.rechargeItemTvType.setText(type.getTypeName());
        if(selected == position){
            viewHolder.rechargeItemCb.setChecked(true);
            viewHolder.itemView.setSelected(true);
        } else {
            viewHolder.rechargeItemCb.setChecked(false);
            viewHolder.itemView.setSelected(false);
        }

    }

    class RechargeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recharge_item_image)
        ImageView rechargeItemImage;
        @BindView(R.id.recharge_item_tv_type)
        TextView rechargeItemTvType;
        @BindView(R.id.recharge_item_cb)
        CheckBox rechargeItemCb;

        public RechargeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
