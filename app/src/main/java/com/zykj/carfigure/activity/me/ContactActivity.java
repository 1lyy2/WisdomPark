package com.zykj.carfigure.activity.me;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
//联系我们

public class ContactActivity extends BaseActivity {


    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.rel_service)
    RelativeLayout relService;
    @BindView(R.id.tv_door)
    TextView tvDoor;
    @BindView(R.id.rel_door)
    RelativeLayout relDoor;
    @BindView(R.id.rel_accounts)
    RelativeLayout relAccounts;

    @Override
    public void onCreatePresenter() {

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.contact_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.rel_service, R.id.rel_door, R.id.rel_accounts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_service:
                String phone = tvService.getText().toString().trim();
                callPhone(phone);
                break;
            case R.id.rel_door:
                break;
            case R.id.rel_accounts:
                break;
        }
    }

    /**
     * 拨打电话
     *
     * @param phone 电话号码
     */
    private void callPhone(String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        } else {

        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);

    }
}
