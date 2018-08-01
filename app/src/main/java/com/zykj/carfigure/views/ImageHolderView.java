package com.zykj.carfigure.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.zykj.carfigure.R;
import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.utils.GlideUtil;

/**
 * Created by Sai on 15/8/4.
 * 本地图片Holder例子
 */
public class ImageHolderView extends Holder<Banner> {
    private ImageView imageView;
    private Context context;

    public ImageHolderView(View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.banner_image);
    }

    @Override
    public void updateUI(Banner data) {
        String url = data.getUrl();
        GlideUtil.loadNoOptions(context,url,imageView);
    }

}
