package com.zykj.carfigure.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zykj.carfigure.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public abstract class BaseRecylerAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;

    protected List<T> mList;

    protected LayoutInflater mInflater;

    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Object item, int position);
//        void onItemLongClick(View view, int position);
    }

    public BaseRecylerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    protected View createView(ViewGroup parent, int layId) {
        return mInflater.inflate(layId, parent, false);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, mList.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

    public void setList(List<T> list) {
        if (list != null) {
            mList = list;
        } else {
            mList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public List<T> getList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }

    public void addItem(T t) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.add(t);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.clear();
        notifyDataSetChanged();
    }

    public void setFormatText(TextView tv, int reId, String value) {
        String text = mContext.getString(reId);
        text = String.format(text, value);
        tv.setText(text);
    }
}
