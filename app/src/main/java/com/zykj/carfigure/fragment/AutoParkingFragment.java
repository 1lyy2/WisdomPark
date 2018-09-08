package com.zykj.carfigure.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.MeNeedParkingActivity;
import com.zykj.carfigure.activity.me.MyCarActivity;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.BaseFragment;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.NearCarPark;
import com.zykj.carfigure.entity.ZyCloudStatus;
import com.zykj.carfigure.mvp.presenter.StopCarPresenter;
import com.zykj.carfigure.mvp.presenter.ZyCloudPresenter;
import com.zykj.carfigure.mvp.view.IStopCarView;
import com.zykj.carfigure.mvp.view.IZyCloudVIew;
import com.zykj.carfigure.widget.AlertDialog;
import com.zykj.carfigure.widget.WaterRippleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 自动匹配车位
 */
public class AutoParkingFragment extends BaseFragment implements IStopCarView, IZyCloudVIew {

    @BindView(R.id.wrv_water)
    WaterRippleView wrvWater;
    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    private StopCarPresenter stopCarPresenter;
    private String licensePlateNumber;//车牌号
    private ZyCloudPresenter zyCloudPresenter;

    @Override
    protected void initView(View rootView) {
        // wrvWater.start();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_auto_parking;
    }

    @Override
    protected String getFragmentName() {
        return "";
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    public void onCreatePresenter() {
        stopCarPresenter = new StopCarPresenter(this);
        zyCloudPresenter = new ZyCloudPresenter(this);
    }

    //匹配成功
    private void showAutoSuccess(final String parkNumber) {
        new AlertDialog(getContext()).builder().setTitle("匹配成功")
                .setMsg("泊车号：" + parkNumber)
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopCar(parkNumber);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }

    //匹配失败
    private void showAutoFailed() {
        new AlertDialog(getContext()).builder().setTitle("匹配失败")
                .setMsg("匹配失败，请手动输入泊车号？")
                .setPositiveButton("手动输入", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MeNeedParkingActivity activity = (MeNeedParkingActivity) getActivity();
                        activity.setPageInputNumber();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }

    @OnClick({R.id.lin_select_number, R.id.wrv_water})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wrv_water:
                autoNearPark();
                break;
            case R.id.lin_select_number:
                Intent intent = new Intent(getContext(), MyCarActivity.class);
                intent.putExtra("type", 2);
                startActivityForResult(intent, 4599);
                break;
        }
    }

    @Override
    public void findNearCarParkSuccess(NearCarPark nearCarPark) {
        if (wrvWater != null) {
            wrvWater.stop();
        }
        if (nearCarPark.getStatus() == 200) {
            NearCarPark.NearCarParkBean data = nearCarPark.getData();
            if (data == null) return;
            String carnumber = data.getCarnumber();
            if (carnumber == null || carnumber.equals("")) {
                showAutoFailed();
            } else {
                showAutoSuccess(carnumber);
            }
        } else {
            showAutoFailed();
        }
    }

    @Override
    public void findNearCarParkFailed() {
        if (wrvWater != null) {
            wrvWater.stop();
        }
        showAutoFailed();
    }

    @Override
    public void stopCarSuccess(CommonBack commonBack) {
        if (commonBack == null) return;
        if (commonBack.getStatus() == 200) {
            zyCloudPresenter.sendCommand(Constants.devId, 1);
        } else {
            showToastMsgShort("您已经停车失败");
        }
    }

    @Override
    public void stopCarFailed() {
        hideLoadingView();
        showToastMsgShort("您已经停车失败");
    }

    /**
     * 自动匹配
     */
    private void autoNearPark() {
        licensePlateNumber = tvInputNumber.getText().toString();
        if (licensePlateNumber.isEmpty()) {
            showToastMsgShort("请选择车牌号码");
            return;
        }
        if (wrvWater != null) {
            wrvWater.start();
            stopCarPresenter.findNearCarPark(Constants.Latitude, Constants.Longitude);
        }

    }

    /**
     * 停车
     */
    private void stopCar(String parkNumber) {
        showLoadingView(null, null);
        if (stopCarPresenter == null) return;
        int user_id = Constants.user.getData().getId();
        stopCarPresenter.stopCar(user_id, parkNumber, licensePlateNumber);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SELECTCARNUMBERCODE && requestCode == 4599) {
            CarMessage.Car car = (CarMessage.Car) data.getSerializableExtra("car");
            if (car == null) return;
            String car_license_plate = car.getCar_license_plate();
            if (car_license_plate == null || car_license_plate.equals("")) {
                tvInputNumber.setText("");
            } else {
                tvInputNumber.setText(car_license_plate);
            }
        }
    }

    @Override
    public void sendCommandSuccess(CommonBack commonBack) {
        hideLoadingView();
        if (commonBack.getStatus() == 200) {
            showToastMsgShort("您已经停车成功");
            getActivity().finish();
        }
    }

    @Override
    public void sendCommandFailed() {
        hideLoadingView();
        showToastMsgShort("您已经停车失败");
    }

    @Override
    public void getCarParkStatusSuccess(ZyCloudStatus status) {

    }

    @Override
    public void getCarParkStatusFailed() {

    }
}
