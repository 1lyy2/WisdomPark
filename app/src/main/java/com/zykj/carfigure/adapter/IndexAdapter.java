package com.zykj.carfigure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.entity.IndexFragmentEntity;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/217:44
 * desc   :
 * version: 1.0
 */
public class IndexAdapter extends BaseRecylerAdapter<Object> {
    //头部标记
    private static final int TYPE_HEADER = 0;
    //宫格布局
    private static final int TYPE_FUNTION = 1;
    //Item 头部
    private static final int TYPE_TITLE = 2;
    //附近停车位优选
    private static final int TYPE_NEAR = 3;

    public IndexAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = mList.get(position);
        if(obj instanceof IndexFragmentEntity.Banner){
            return TYPE_HEADER;
        }else if(obj instanceof IndexFragmentEntity.Content){
            return TYPE_FUNTION;
        }else if(obj instanceof String){
            return TYPE_TITLE;
        }else if(obj instanceof IndexFragmentEntity.Near){
            return TYPE_NEAR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            default:
                break;
        }
    }
}
