package com.zykj.carfigure.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zykj.carfigure.R;
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
import com.zykj.carfigure.widget.VerificationCodeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动输入停车
 */
public class EditParkingFragment extends BaseFragment implements IStopCarView, IZyCloudVIew {

    @BindView(R.id.view_verification)
    VerificationCodeView viewVerification;
    @BindView(R.id.btn_park)
    Button btnPark;
    @BindView(R.id.tv_input_number)
    TextView tvInputNumber;
    private String licensePlateNumber;//车牌号
    private StopCarPresenter stopCarPresenter;
    private ZyCloudPresenter zyCloudPresenter;

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_edit_parking;
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

    @OnClick({R.id.lin_select_number, R.id.btn_park})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_select_number:
                Intent intent = new Intent(getContext(), MyCarActivity.class);
                intent.putExtra("type", 2);
                startActivityForResult(intent, 4629);
                break;
            case R.id.btn_park:
                parking();
                break;
        }
    }    //停车

    private void parking() {
        String content = viewVerification.getContent();
        if (content == null || content.length() < 6) {
            showToastMsgShort("请输入正确的泊位编号");
            return;
        }
        licensePlateNumber = tvInputNumber.getText().toString();
        if (licensePlateNumber.isEmpty()) {
            showToastMsgShort("请选择车牌号码");
            return;
        }
        licensePlateNumber = tvInputNumber.getText().toString().trim();
        int user_id = Constants.user_id;
        stopCarPresenter.stopCar(user_id, content, licensePlateNumber);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4629 && resultCode == Constants.SELECTCARNUMBERCODE) {
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
    public void findNearCarParkSuccess(NearCarPark nearCarPark) {

    }

    @Override
    public void findNearCarParkFailed() {

    }

    @Override
    public void stopCarSuccess(CommonBack commonBack) {
        hideLoadingView();
        if (commonBack == null) return;
        if (commonBack.getStatus() == 200) {
            zyCloudPresenter.sendCommand(Constants.devId, 1);
        } else {
            showToastMsgShort("您已经停车失败");
        }
    }

    @Override
    public void stopCarFailed() {
        showToastMsgShort("您已经停车失败");
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
