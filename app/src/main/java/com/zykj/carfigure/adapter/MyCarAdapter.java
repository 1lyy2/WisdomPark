package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventBusUtils;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.utils.StrUtil;

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
    private int type;
    private int selected = -1;


    public MyCarAdapter(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCarHolder(LayoutInflater.from(context).inflate(R.layout.mycar_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyCarHolder myCarHolder = (MyCarHolder) holder;
        if (type == 2) {
            myCarHolder.btnDelMycar.setVisibility(View.GONE);
            myCarHolder.checkBox.setVisibility(View.VISIBLE);
        } else {
            myCarHolder.btnDelMycar.setVisibility(View.VISIBLE);
            myCarHolder.checkBox.setVisibility(View.GONE);
        }
        if (type == 2) {
            if (selected == position) {
                myCarHolder.checkBox.setChecked(true);
                myCarHolder.itemView.setSelected(true);

            } else {
                myCarHolder.checkBox.setChecked(false);
                myCarHolder.itemView.setSelected(false);

            }
        }
        final CarMessage.Car car = (CarMessage.Car) mList.get(position);
        if (car == null) {

        } else {
              initData(myCarHolder,car);
        }
        myCarHolder.btnDelMycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event<CarMessage.Car> event = new Event<>(EventCode.DELETECAR,car);
                EventBusUtils.sendEvent(event);
            }
        });

    }

    class MyCarHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mycar_number)
        TextView tvMycarNumber;
        @BindView(R.id.btn_del_mycar)
        Button btnDelMycar;
        @BindView(R.id.mycar_cardview)
        CardView mycarCardview;
        @BindView(R.id.cb_mycar)
        CheckBox checkBox;
        @BindView(R.id.tv_car_tips)
        TextView tvCarTips;

        public MyCarHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void initData(MyCarHolder carHolder, CarMessage.Car car) {
        //车牌号
        String car_license_plate =car.getCar_license_plate();
        if (StrUtil.isEmpty(car_license_plate)) {
            car_license_plate = "";
        }
        //品牌
        String car_brand =car.getCar_brand();
        if (StrUtil.isEmpty(car_brand)) {
            car_brand = "";
        }
        //型号
        String car_model = car.getCar_model();
        if (StrUtil.isEmpty(car_model)) {
            car_model = "";
        }
        //配置
        String car_to_configure = car.getCar_to_configure();
        if (StrUtil.isEmpty(car_to_configure)) {
            car_to_configure = "";
        }
        //车身颜色
        String car_color = car.getCar_color();
        if (StrUtil.isEmpty(car_color)) {
            car_color = "";
        }
        carHolder.tvMycarNumber.setText(car_license_plate);
        StringBuilder sb = new StringBuilder();
        sb.append(car_brand);
        sb.append(" ");
        sb.append(car_model);
        sb.append(" ");
        sb.append(car_to_configure);
        sb.append(" ");
        sb.append(car_color);
        carHolder.tvCarTips.setText(sb.toString());
    }

}
