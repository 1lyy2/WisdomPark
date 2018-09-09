package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.ReservedParking;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1615:26
 * desc   : 我的预约适配器
 * version: 1.0
 */
public class MyReserveAdapter extends BaseRecylerAdapter {
    private int type;

    public MyReserveAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyReserveHolder(LayoutInflater.from(mContext).inflate(R.layout.my_reserve_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservedParking.OrderParking orderParking = (ReservedParking.OrderParking) mList.get(position);
        if(orderParking==null) return;
        MyReserveHolder reserveHolder = (MyReserveHolder) holder;
        if (type == 1) {
            reserveHolder.linRightBtn.setVisibility(View.VISIBLE);
        } else {
            reserveHolder.linRightBtn.setVisibility(View.GONE);
        }
        //车牌号
        String license_number = orderParking.getLicense_number();
        if(license_number==null||license_number.isEmpty()){
            reserveHolder.tvReserveNumber.setText("");
        }else{
            reserveHolder.tvReserveNumber.setText(license_number);
        }
        //预约地址
        String streetName = orderParking.getStreetName();
        String order_carnumber = orderParking.getOrder_carnumber();
        String address= streetName+" :"+order_carnumber;
        reserveHolder.tvReserveAddress.setText(address);
        //预约时间
        String order_time = orderParking.getOrder_time();
        reserveHolder.tvReserveTime.setText(order_time);
        //预约人信息
        String username = orderParking.getUsername();
        if(username==null||username.isEmpty()){
            reserveHolder.tvReserveUsername.setText("");
        }else{
            reserveHolder.tvReserveUsername.setText(username);
        }
        //预约人电话号码
        String user_phone = orderParking.getUser_phone();
        if(user_phone==null||user_phone.isEmpty()){
            reserveHolder.tvReserveUserphone.setText("");
        }else{
            reserveHolder.tvReserveUserphone.setText(user_phone);
        }
    }

    class MyReserveHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_reserve_number)
        TextView tvReserveNumber;
        @BindView(R.id.tv_reserve_address)
        TextView tvReserveAddress;
        @BindView(R.id.tv_reserve_time)
        TextView tvReserveTime;
        @BindView(R.id.lin_left_my)
        LinearLayout linLeftMy;
        @BindView(R.id.tv_reserve_username)
        TextView tvReserveUsername;
        @BindView(R.id.tv_reserve_userphone)
        TextView tvReserveUserphone;
        @BindView(R.id.rel_left)
        RelativeLayout relLeft;
        @BindView(R.id.btn_reserve_cancel)
        Button btnReserveCancel;
        @BindView(R.id.lin_right_btn)
        LinearLayout linRightBtn;

        public MyReserveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
