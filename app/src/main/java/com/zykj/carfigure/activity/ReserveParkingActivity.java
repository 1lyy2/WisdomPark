package com.zykj.carfigure.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.activity.me.MyCarActivity;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CarPark;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.ZyCloudStatus;
import com.zykj.carfigure.mvp.presenter.ReservedParkingPresenter;
import com.zykj.carfigure.mvp.presenter.ZyCloudPresenter;
import com.zykj.carfigure.mvp.view.IReservedParkingView;
import com.zykj.carfigure.mvp.view.IZyCloudVIew;
import com.zykj.carfigure.widget.customdatepicker.CustomDatePicker;
import com.zykj.carfigure.widget.popup.CarNumberSelectPopup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

//预定车位
public class ReserveParkingActivity extends UserBaseActivity implements CarNumberSelectPopup.SelectCarNumberInterface, IReservedParkingView,IZyCloudVIew {


    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    @BindView(R.id.tv_input_address)
    TextView tvInputAddress;
    @BindView(R.id.tv_input_time)
    TextView tvInputTime;
    @BindView(R.id.tv_input_username)
    EditText tvInputUsername;
    @BindView(R.id.tv_input_userphone)
    EditText tvInputUserphone;
    @BindView(R.id.btn_confirm_reserve)
    Button btnConfirmReserve;

    private CustomDatePicker customDatePicker;

    public static String ORDERADDRESS = "orderaddress";

    private int street_id = 0;
    private String parkNumber = "";
    private String streetName = "";

    private ReservedParkingPresenter reservedParkingPresenter;
    private ZyCloudPresenter zyCloudPresenter;

    @Override
    public void onCreatePresenter() {
        reservedParkingPresenter = new ReservedParkingPresenter(this);
        zyCloudPresenter = new ZyCloudPresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reserve_parking;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        initDatePicker();
        Intent intent = getIntent();
        tvInputNumber.setText(Constants.car_license_plate);
        if (intent != null) {
            CarPark.CarParkDetail carport = (CarPark.CarParkDetail) intent.getSerializableExtra(ReserveParkingActivity.ORDERADDRESS);
            if (carport == null) {
            } else {
                street_id = carport.getStreetId();
                streetName = carport.getStreetName();
                parkNumber = carport.getCarnumber();
                tvInputAddress.setText(streetName + ":" + parkNumber);
            }
        }
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.reserve_pariking_sting);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick({R.id.tv_input_number, R.id.tv_input_address, R.id.tv_input_time, R.id.btn_confirm_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_input_number:
                Intent intent = new Intent(ReserveParkingActivity.this, MyCarActivity.class);
                intent.putExtra("type", 2);
                startActivityForResult(intent, 4569);
                break;
            case R.id.tv_input_address:
                //地址选择
                launchActivity(SelectAddressActivity.class);
                break;
            case R.id.tv_input_time:
                // 日期格式为yyyy-MM-dd HH:mm
                //customDatePicker.show(time);
                break;
            case R.id.btn_confirm_reserve:
                //立即预约
                reserveParking();
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tvInputTime.setText(now);

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvInputTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void selectCarNumber(Object obj) {
        String number = (String) obj;
        tvInputNumber.setText(number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4569 && resultCode == Constants.SELECTCARNUMBERCODE) {
            CarMessage.Car car = (CarMessage.Car) data.getSerializableExtra("car");
            if (car == null) return;
            Constants.car_license_plate = car.getCar_license_plate();
            if (Constants.car_license_plate == null || Constants.car_license_plate.equals("")) {
                tvInputNumber.setText("");
            } else {
                tvInputNumber.setText(Constants.car_license_plate);
            }
        }
    }

    /**
     * 立即预约
     */
    private void reserveParking() {

        //车牌好
        String carNumber = tvInputNumber.getText().toString().trim();
        if (carNumber == null || carNumber.isEmpty()) {
            showToastMsgShort("请输入车牌号");
            return;
        }
        //停车位置
        String address = tvInputAddress.getText().toString();
        if (address == null || address.isEmpty()) {
            showToastMsgShort("请输入停车位");
            return;
        }
        String orderTime = tvInputTime.getText().toString();
        String userName = tvInputUsername.getText().toString();
        String userPhone = tvInputUserphone.getText().toString();
        reservedParkingPresenter.reservedParking(Constants.user_id, carNumber, street_id, parkNumber, orderTime, userName, userPhone,streetName);
        showLoadingView(null, null);
    }

    @Override
    public void reservedParkingSuccess(CommonBack commonBack) {
        if (commonBack.getStatus() == 200) {
            zyCloudPresenter.sendCommand(Constants.devId,1);
        } else {
            showToastMsgShort("预约车位失败");
        }
    }

    @Override
    public void reservedParkingFailed() {
        hideLoadingView();
        showToastMsgShort("预约车位失败");
    }

    @Override
    public void sendCommandSuccess(CommonBack commonBack) {
        hideLoadingView();
        if(commonBack.getStatus()==200){
            showToastMsgShort("预约车位成功,请到我的预约中查看预约信息");
            finish();
        }

    }

    @Override
    public void sendCommandFailed() {
        hideLoadingView();
        showToastMsgShort("预约车位失败");
    }

    @Override
    public void getCarParkStatusSuccess(ZyCloudStatus status) {

    }

    @Override
    public void getCarParkStatusFailed() {

    }
}
