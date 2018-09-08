package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.entity.AddressBean;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1015:21
 * desc   : 地图搜索地点的适配器
 * version: 1.0
 */
public class AddressSearchAdapter extends BaseRecylerAdapter {
    public AddressSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new MapSerachHolder(LayoutInflater.from(mContext).inflate(R.layout.mapsearch_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int itemViewType = holder.getItemViewType();
        MapSerachHolder mapSerachHolder = (MapSerachHolder) holder;
        AddressBean tip = (AddressBean) mList.get(position);
        if (tip == null) return;
        String name = tip.getTitle();
        if (name == null || name.equals("")) {
            mapSerachHolder.tvSearchName.setText("");
        } else {
            mapSerachHolder.tvSearchName.setText(name+"("+tip.getText()+")");
        }
        String address = tip.getAddress();
        if (address == null || address.equals("")) {
            mapSerachHolder.tvSearchNameDetail.setText("");
        } else {
            mapSerachHolder.tvSearchNameDetail.setText(address);
        }
        LatLng markerPosition = new LatLng(tip.getLatitude(), tip.getLongitude());
        float distance = AMapUtils.calculateLineDistance(markerPosition, Constants.latLng);
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String num = df.format((float)distance/1000);//返回的是String类型
        mapSerachHolder.tvSearchRange.setText(num + "KM");

    }

    class MapSerachHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_search_range)
        TextView tvSearchRange;
        @BindView(R.id.tv_search_name)
        TextView tvSearchName;
        @BindView(R.id.tv_search_name_detail)
        TextView tvSearchNameDetail;

        public MapSerachHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
