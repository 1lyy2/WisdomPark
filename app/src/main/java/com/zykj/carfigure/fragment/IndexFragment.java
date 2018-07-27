package com.zykj.carfigure.fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.utils.ToastManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

@BindEventBus
public class IndexFragment extends BaseFragment {



    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    protected String getFragmentName() {
        return getString(R.string.fragment_index);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {

    }
    /**
     *
     * 从发布者那里得到eventbus传送过来的数据
     *
     * 加上@Subscribe以防报错：its super classes have no public methods with the @Subscribe annotation
     *
     * @param event
     */
    @Subscribe
    public void onEvent(Event event){
       // ToastManager.showShortToast(getContext(),event.getData().toString());
    }


}
