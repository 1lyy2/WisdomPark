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
 * date   : 2018/8/1617:52
 * desc   :
 * version: 1.0
 */
public class CarNumberSelectAdapter extends BaseRecylerAdapter {

    public CarNumberSelectAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarNumberSelectHolder(LayoutInflater.from(mContext).inflate(R.layout.car_number_select_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CarNumberSelectHolder carNumberSelectHolder = (CarNumberSelectHolder) holder;
        Object o = mList.get(position);
        if(o==null){
            carNumberSelectHolder.number.setText("");
        }else {
            carNumberSelectHolder.number.setText(o.toString());
        }

    }

    class CarNumberSelectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        public CarNumberSelectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
