package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.CarPark;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2310:44
 * desc   : 空车位的列表适配器
 * version: 1.0
 */
public class FreeParkingAdapter extends BaseRecylerAdapter {
    private OrderListener orderListener;

    public FreeParkingAdapter(Context context, OrderListener orderListener) {
        super(context);
        this.orderListener = orderListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreeParkingHolder(LayoutInflater.from(mContext).inflate(R.layout.free_park_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        FreeParkingHolder freeParkingHolder = (FreeParkingHolder) holder;
        if (freeParkingHolder == null) return;
        final CarPark.CarParkDetail carport = (CarPark.CarParkDetail) mList.get(position);
        if (carport == null) return;
        String streetName = carport.getStreetName();
        if(streetName==null||streetName.equals("")){
            freeParkingHolder.tvParkingStreetname.setText("");
        }else{
            freeParkingHolder.tvParkingStreetname.setText(streetName);
        }
        String carportNumber = carport.getCarnumber();
        if (carportNumber == null || carportNumber.equals("")) {
            freeParkingHolder.tvParkingNumber.setText("");
        } else {
            freeParkingHolder.tvParkingNumber.setText(carportNumber);
        }
        freeParkingHolder.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListener.order(carport);
            }
        });

    }

    class FreeParkingHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_parking_number)
        TextView tvParkingNumber;
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_parking_streetname)
        TextView tvParkingStreetname;

        public FreeParkingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OrderListener {
        void order(CarPark.CarParkDetail carPark);
    }

}
