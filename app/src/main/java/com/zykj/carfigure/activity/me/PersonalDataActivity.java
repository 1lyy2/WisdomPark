package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.content.Intent;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.UserBaseActivity;

import java.util.ArrayList;

import butterknife.OnClick;

//个人资料
public class PersonalDataActivity extends UserBaseActivity {
   private int REQUEST_CODE = 1001;
    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.personal_data_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {
        //单选并剪裁
        ImageSelector.builder()
                .useCamera(true) // 设置是否使用拍照
                .setCrop(true)  // 设置是否使用图片剪切功能。
                .setSingle(true)  //设置是否单选
                .start(this, REQUEST_CODE); // 打开相册
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
        }
    }
}
