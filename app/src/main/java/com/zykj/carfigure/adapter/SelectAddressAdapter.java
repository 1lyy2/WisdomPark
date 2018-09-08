package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2011:17
 * desc   : 地址选择的适配器
 * version: 1.0
 */
public class SelectAddressAdapter extends BaseRecylerAdapter {
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
    @BindView(R.id.image_route)
    ImageView imageRoute;
    @BindView(R.id.rel_route)
    RelativeLayout relRoute;

    public SelectAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectAddressHolder(LayoutInflater.from(mContext).inflate(R.layout.selec_address_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SelectAddressHolder addressHolder = (SelectAddressHolder) holder;
        final Street.StreetDetail street = (Street.StreetDetail) mList.get(position);
        if (street == null) return;
        addressHolder.tvStreetName.setText(street.getStreetName());
        addressHolder.tvStreetLength.setText(String.valueOf(street.getStreetLength()));
        addressHolder.tvFreePark.setText("空车位:" + street.getEmptyPark());
        addressHolder.tvTotalPark.setText("总车位:" + street.getTotalParkNumber());
        addressHolder.relGoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event(EventCode.SELECTCARDETAIL, street));
            }
        });
        addressHolder.relRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.sendEvent(new Event(EventCode.SELECTCARPARK, street));
            }
        });
    }

    class SelectAddressHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.image_route)
        ImageView imageRoute;
        @BindView(R.id.rel_route)
        RelativeLayout relRoute;

        public SelectAddressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
