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

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/1314:48
 * desc   : 历史搜索记录适配器
 * version: 1.0
 */
public class SearchHistoryAdapter extends BaseRecylerAdapter {
    public SearchHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(mContext).inflate(R.layout.search_history_item, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String o = (String) mList.get(position);
        HistoryHolder historyHolder = (HistoryHolder) holder;
        if (o == null || o.equals("")) {
            historyHolder.tvHistory.setText("");
        } else {
            historyHolder.tvHistory.setText(o);
        }
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_history)
        TextView tvHistory;

        public HistoryHolder(View itemView) {
            super(itemView);
        }
    }

}
