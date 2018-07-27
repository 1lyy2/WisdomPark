package com.zykj.carfigure.mvp;

import android.app.Activity;

import com.zykj.carfigure.base.BaseInterface;

public interface BaseView {
    BaseInterface getBaseInterface();
    Activity getActivity();
}
