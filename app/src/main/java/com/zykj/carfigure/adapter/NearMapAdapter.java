package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.utils.ToastManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearMapAdapter extends BaseRecylerAdapter<Street> {



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
        Street street = mList.get(position);
        if(street==null) return;
        nearMapHolder.tvStreetName.setText(street.getStreetName());
        nearMapHolder.tvStreetLength.setText(String.valueOf(street.getStreetLength()));
        nearMapHolder.tvFreePark.setText("空车位:"+street.getFreePark());
        nearMapHolder.tvTotalPark.setText("总车位:"+street.getTotalPark());
    }

    @OnClick({R.id.rel_go_detail, R.id.rel_route, R.id.rel_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_go_detail:
                ToastManager.showLongToast(mContext,"适配器点击");
                break;
            case R.id.rel_route:
                break;
            case R.id.rel_navigation:
                break;
        }
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

        public NearMapHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
