package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.Street;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3110:28
 * desc   : 街道列表适配器
 * version: 1.0
 */
public class StreetListAdapter extends BaseRecylerAdapter {

    public StreetListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StreetHolder(createView(parent, R.layout.street_list_item));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Street.StreetDetail street = (Street.StreetDetail) mList.get(position);
        StreetHolder streetHolder = (StreetHolder) holder;
        int freePark = street.getEmptyPark();
        streetHolder.tvFreeItemPark.setText(freePark + "");
        if (freePark > 0) {
            streetHolder.tvFreeItemPark.setTextColor(mContext.getResources().getColor(R.color.tab_sel_color));
        } else {
            streetHolder.tvFreeItemPark.setTextColor(mContext.getResources().getColor(R.color.color_fe3e3c));
        }
        streetHolder.tvStreetItemName.setText(street.getStreetName());
        streetHolder.tvStreetItemLength.setText(street.getStreetLength() + "km");
    }

    class StreetHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_street_item_name)
        TextView tvStreetItemName;
        @BindView(R.id.tv_street_item_length)
        TextView tvStreetItemLength;
        @BindView(R.id.tv_free_item_park)
        TextView tvFreeItemPark;

        public StreetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
