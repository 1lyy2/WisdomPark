package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.WaitPay;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2017:55
 * desc   :
 * version: 1.0
 */
public class WaitPayAdapter extends BaseRecylerAdapter {
    private ToPayListener toPayListener;

    public WaitPayAdapter(Context context ,ToPayListener toPayListener) {
        super(context);
        this.toPayListener = toPayListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WaitPayHolder(LayoutInflater.from(mContext).inflate(R.layout.wait_pay_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WaitPayHolder waitPayHolder = (WaitPayHolder) holder;
        final WaitPay waitPay = (WaitPay) mList.get(position);
        if(waitPay==null) return;
        waitPayHolder.tvPayCarnumber.setText(waitPay.getCarNumber());
        waitPayHolder.tvPayAddress.setText(waitPay.getAddress());
        waitPayHolder.tvPayTime.setText(waitPay.getParkTime());
        waitPayHolder.tvTime.setText(waitPay.getParkCountTime());
        waitPayHolder.tvPayMoney.setText("￥"+waitPay.getPayMoney()+"");
        waitPayHolder.tvTopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPayListener.toPay(waitPay);
            }
        });
    }

    class WaitPayHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_pay_money)
        TextView tvPayMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_topay)
        TextView tvTopay;
        @BindView(R.id.tv_pay_carnumber)
        TextView tvPayCarnumber;
        @BindView(R.id.tv_pay_address)
        TextView tvPayAddress;
        @BindView(R.id.tv_pay_time)
        TextView tvPayTime;

        public WaitPayHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface  ToPayListener{
        void toPay(WaitPay waitPay);
    }

}
