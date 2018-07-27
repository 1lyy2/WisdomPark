package com.zykj.carfigure.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseFragment;

public class MeFragment extends BaseFragment {

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected String getFragmentName() {
        return getString(R.string.fragment_me);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {

    }
}
