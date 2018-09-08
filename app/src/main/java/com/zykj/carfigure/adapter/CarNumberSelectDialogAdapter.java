package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.CarMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3111:32
 * desc   : 车牌选择的适配器
 * version: 1.0
 */
public class CarNumberSelectDialogAdapter extends BaseRecylerAdapter {
    private boolean showTitle;

    public CarNumberSelectDialogAdapter(Context context, boolean showTitle) {
        super(context);
        this.showTitle = showTitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarNumberSelectDialogHolder(LayoutInflater.from(mContext).inflate(R.layout.car_number_select_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CarNumberSelectDialogHolder mHolder = (CarNumberSelectDialogHolder) holder;
        CarMessage.Car car = (CarMessage.Car) mList.get(position);
        if (car == null) return;
        String car_license_plate = car.getCar_license_plate();
        if (car_license_plate == null || car_license_plate.equals("")) {
            mHolder.txtCarnumber.setText("");
        } else {
            mHolder.txtCarnumber.setText(car_license_plate);
        }
        // 背景图片
        if (position == 1) {
            if (showTitle) {
                mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
            } else {
                mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_single_selector);
            }
        } else {
            if (showTitle) {
                if (position >= 1 && position < mList.size()) {
                    mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                } else {
                    mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                }
            } else {
                if (position == 1) {
                    mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_top_selector);
                } else if (position < mList.size()) {
                    mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                } else {
                    mHolder.txtCarnumber.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                }
            }
        }
    }

    class CarNumberSelectDialogHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_carnumber)
        TextView txtCarnumber;

        public CarNumberSelectDialogHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }
}
