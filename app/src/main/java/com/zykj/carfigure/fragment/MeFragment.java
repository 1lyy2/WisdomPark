package com.zykj.carfigure.fragment;

import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.MyBillsActivity;
import com.zykj.carfigure.activity.me.AboutActivity;
import com.zykj.carfigure.activity.me.BalanceActivity;
import com.zykj.carfigure.activity.me.CommonProblemActivity;
import com.zykj.carfigure.activity.me.ContactActivity;
import com.zykj.carfigure.activity.me.MyCarActivity;
import com.zykj.carfigure.activity.me.MyCouponsActivity;
import com.zykj.carfigure.activity.me.ParkRecordActivity;
import com.zykj.carfigure.activity.me.PersonalDataActivity;
import com.zykj.carfigure.activity.me.SettingActivity;
import com.zykj.carfigure.activity.me.SuggestActivity;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.utils.StatusBarUtil;
import com.zykj.carfigure.widget.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class MeFragment extends BaseFragment {

    @BindView(R.id.fake_status_bar)
    View statusView;
    @BindView(R.id.round_user_avtar)
    RoundedImageView roundUserAvtar;
    @BindView(R.id.tv_useranme)
    TextView tvUseranme;
    @BindView(R.id.tv_mycoupon)
    TextView tvMycoupon;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_part_record)
    TextView tvPartRecord;

    public static boolean isAutoRefreshUserInfo = false;//当操作用户基础数据时 使其自动加载更新

    @Override
    protected void initView(View rootView) {
        initStatus();
        init();

    }
    private void init() {
        if(app.isLogined()){
            User loginUser = app.getLoginUser();
            tvUseranme.setText(loginUser.getData().getUsername());
        }else{
            tvUseranme.setText("请点击头像登录");
        }
    }


    private void initStatus() {
        statusView = rootView.findViewById(R.id.fake_status_bar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.height = StatusBarUtil.getStatusBarHeight(getActivity());
        statusView.setLayoutParams(lp);
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

    @OnClick({R.id.round_user_avtar, R.id.lin_money, R.id.user_setting, R.id.lin_mycoupon, R.id.lin_mycar,
            R.id.lin_park_record, R.id.lin_about, R.id.lin_connect, R.id.lin_complaints, R.id.lin_problem, R.id.lin_bills})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.round_user_avtar:
                //头像
                launchActivity(PersonalDataActivity.class);
                break;
            case R.id.lin_money:
                //余额
                launchActivity(BalanceActivity.class);
                break;
            case R.id.user_setting:
                //设置
                launchActivity(SettingActivity.class);
                break;
            case R.id.lin_mycoupon:
                //优惠劵
                launchActivity(MyCouponsActivity.class);
                break;
            case R.id.lin_mycar:
                //我的车辆
                launchActivity(MyCarActivity.class);
                break;
            case R.id.lin_park_record:
                //停车记录
                launchActivity(ParkRecordActivity.class);
                break;
            case R.id.lin_about:
                //关于
                launchActivity(AboutActivity.class);
                break;
            case R.id.lin_connect:
                //联系我们
                launchActivity(ContactActivity.class);
                break;
            case R.id.lin_complaints:
                //投诉建议
                launchActivity(SuggestActivity.class);
                break;
            case R.id.lin_problem:
                launchActivity(CommonProblemActivity.class);
                break;
            case R.id.lin_bills:
                //我的账单
                launchActivity(MyBillsActivity.class);
                break;
            default:
                break;
        }
    }
}
