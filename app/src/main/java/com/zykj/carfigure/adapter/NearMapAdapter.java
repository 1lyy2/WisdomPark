package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.eventbus.EventCode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearMapAdapter extends BaseRecylerAdapter {

    public NearMapAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NearMapHolder(createView(parent, R.layout.nearmap_cardview_item));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NearMapHolder nearMapHolder = (NearMapHolder) holder;
        final Street.StreetDetail street =(Street.StreetDetail) mList.get(position);
        if (street == null) return;
        nearMapHolder.tvStreetName.setText(street.getStreetName());
        nearMapHolder.tvStreetLength.setText(String.valueOf(street.getStreetLength()+" KM"));
        nearMapHolder.tvFreePark.setText("空车位:" + street.getEmptyPark());
        nearMapHolder.tvTotalPark.setText("总车位:" + street.getTotalParkNumber());
        nearMapHolder.relGoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event(EventCode.NEARCARPARKDETAIL, street));
            }
        });
        nearMapHolder.relRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event(EventCode.ROUTE, street));
            }
        });
        nearMapHolder.relNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event(EventCode.NAVIGATION, street));
            }
        });
    }

    class NearMapHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_street_name)
        TextView tvStreetName;
        @BindView(R.id.tv_street_length)
        TextView tvStreetLength;
        @BindView(R.id.tv_free_park)
        TextView tvFreePark;
        @BindView(R.id.tv_total_park)
        TextView tvTotalPark;
        @BindView(R.id.rel_go_detail)
        RelativeLayout relGoDetail;
        @BindView(R.id.rel_route)
        RelativeLayout relRoute;
        @BindView(R.id.rel_navigation)
        RelativeLayout relNavigation;

        public NearMapHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
