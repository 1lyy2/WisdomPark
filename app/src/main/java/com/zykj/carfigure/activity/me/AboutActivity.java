package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;

//关于我们
public class AboutActivity extends BaseActivity {


    @BindView(R.id.tv_about_introduction)
    TextView tvAboutIntroduction;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        tvAboutIntroduction.setText("公司简介：车图是xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        SpannableStringBuilder span = new SpannableStringBuilder("缩进"+tvAboutIntroduction.getText());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAboutIntroduction.setText(span);

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.about_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

}
