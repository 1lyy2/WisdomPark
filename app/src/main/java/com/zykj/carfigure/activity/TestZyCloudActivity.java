package com.zykj.carfigure.activity;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.ZyCloudStatus;
import com.zykj.carfigure.log.Log;
import com.zykj.carfigure.mvp.presenter.InitZyCloudPresenter;
import com.zykj.carfigure.mvp.presenter.ZyCloudPresenter;
import com.zykj.carfigure.mvp.view.IInitZyCloudView;
import com.zykj.carfigure.mvp.view.IZyCloudVIew;

import butterknife.BindView;
import butterknife.OnClick;

public class TestZyCloudActivity extends BaseActivity implements IZyCloudVIew, IInitZyCloudView {

    @BindView(R.id.tv_init)
    TextView tvInit;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.btn_status)
    Button btnStatus;
    @BindView(R.id.tv_car_status)
    TextView tvCarStatus;
    @BindView(R.id.btn_yijin)
    Button btnYijin;
    @BindView(R.id.btn_dici)
    Button btnDici;
    private ZyCloudPresenter zyCloudPresenter;
    private InitZyCloudPresenter initZyCloudPresenter;
    int type = 0;
    int init_type = 1;
    private int TIME = 10000;  //每隔10s执行一次.
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public void onCreatePresenter() {
        zyCloudPresenter = new ZyCloudPresenter(this);
        initZyCloudPresenter = new InitZyCloudPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_test_zy_cloud;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
    }

    @Override
    protected String getActivityName() {
        return "测试";
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick({R.id.btn_open, R.id.btn_close, R.id.btn_status, R.id.btn_yijin, R.id.btn_dici, R.id.btn_shuju})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                type = 1;
                showLoadingView(null, null, false);
                zyCloudPresenter.sendCommand(Constants.devId, 1);
                break;
            case R.id.btn_close:
                type = 0;
                showLoadingView(null, null, false);
                zyCloudPresenter.sendCommand(Constants.devId, 0);
                break;
            case R.id.btn_status:
                timer1();
                break;
            case R.id.btn_yijin:
                //1.硬件初始化 2.数据初始化 3.磁标初始化
                //硬件初始化
                init_type = 1;
                initZyCloudPresenter.initZyCloud(Constants.devId, 1);
                break;
            case R.id.btn_shuju:
                //数据初始化
                init_type = 2;
                initZyCloudPresenter.initZyCloud(Constants.devId, 2);
                break;
            case R.id.btn_dici:
                //地磁初始化
                init_type = 3;
                initZyCloudPresenter.initZyCloud(Constants.devId, 3);
                break;

        }
    }

    @Override
    public void sendCommandSuccess(CommonBack commonBack) {
        hideLoadingView();
        if (type == 0) {
            tvStatus.setText("关灯成功");
        } else if (type == 1) {
            tvStatus.setText("开灯成功");
        }

    }

    @Override
    public void sendCommandFailed() {
        hideLoadingView();
        if (type == 0) {
            tvStatus.setText("关灯失败");
        } else if (type == 1) {
            tvStatus.setText("开灯失败");
        }
    }

    @Override
    public void getCarParkStatusSuccess(ZyCloudStatus status) {
        if (status.getStatus() == 200) {
            int data = status.getData();
            if (tvCarStatus == null) return;
            if (data == 0) {
                tvCarStatus.setText("该车位无车");
                tvCarStatus.setTextColor(getResources().getColor(R.color.commom_text_color));
            } else if (data == 1) {
                tvCarStatus.setText("该车位有车");
                tvCarStatus.setTextColor(getResources().getColor(R.color.color_fe3e3c));
            }

        }
    }

    @Override
    public void getCarParkStatusFailed() {
        if (tvCarStatus == null) return;
        tvCarStatus.setText("获取车位状态失败");
    }

    // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)
    public void timer1() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, TIME);
                    zyCloudPresenter.getCarParkingStatus(Constants.devId);
                    Log.i("print", "1-------------");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        handler.postDelayed(runnable, TIME); // 在初始化方法里.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

    }

    @Override
    public void initZyCloudSuccess(CommonBack commonBack) {
        //1.硬件初始化 2.数据初始化 3.磁标初始化
        if (commonBack.getStatus() == 200) {
            if (init_type == 1) {
                tvInit.setText("硬件初始化成功");
            } else if (init_type == 2) {
                tvInit.setText("数据初始化成功");
            } else if (init_type == 3) {
                tvInit.setText("磁标初始化成功");
            } else {
                tvInit.setText("其他初始化失败");
            }
        }
    }

    @Override
    public void initZyCloudFailed() {
        if (init_type == 1) {
            tvInit.setText("硬件初始化失败");
        } else if (init_type == 2) {
            tvInit.setText("数据初始化失败");
        } else if (init_type == 3) {
            tvInit.setText("磁标初始化失败");
        } else {
            tvInit.setText("其他初始化失败");
        }
    }
}
