package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.parkingwang.keyboard.KeyboardInputController;
import com.parkingwang.keyboard.OnInputChangedListener;
import com.parkingwang.keyboard.PopupKeyboard;
import com.parkingwang.keyboard.view.InputView;
import com.zykj.carfigure.MainActivity;
import com.zykj.carfigure.R;
import com.zykj.carfigure.base.BaseActivity;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.mvp.presenter.CarMannagePresenter;
import com.zykj.carfigure.mvp.view.ICarMannageView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

//添加车辆
public class AddCarActivity extends BaseActivity<CarMannagePresenter> implements ICarMannageView {
    @BindView(R.id.input_view)
    InputView inputView;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    @BindView(R.id.btn_addcar)
    Button btnAddCar;
    private PopupKeyboard mPopupKeyboard;
    private int carType = 1; //车辆是否为新能源 0新能源，1非新能源
    private boolean isputCompleted;//是否填写完成
    private String carNumber;//车牌号
    private CarMannagePresenter carMannagePresenter;
    public static int resultCode = 10004;

    @Override
    public void onCreatePresenter() {
        carMannagePresenter = new CarMannagePresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        setKeyboard();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    carType = 0;
                } else {
                    carType = 1;
                }
            }
        });
    }

    @Override
    protected String getActivityName() {
        return getString(R.string.add_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void setKeyboard() {
        // 创建弹出键盘
        mPopupKeyboard = new PopupKeyboard(this);
        mPopupKeyboard.getKeyboardEngine().setLocalProvinceName(MainActivity.Province);
        // 弹出键盘内部包含一个KeyboardView，在此绑定输入两者关联。
        mPopupKeyboard.attach(inputView, this);
        // KeyboardInputController提供一个默认实现的新能源车牌锁定按钮
        mPopupKeyboard.getController()
                .setDebugEnabled(true)
                .bindLockTypeProxy(new CheckBoxProxyImpl(checkBox) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
                        if (isNewEnergyType) {
                            //showToastMsgShort("新能源");
                        } else {
                            // showToastMsgShort("非新能源");
                        }
                    }
                });
        mPopupKeyboard.getController().addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {
                isputCompleted = false;
            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                isputCompleted = true;
                carNumber = number;
                mPopupKeyboard.dismiss(AddCarActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 默认选中第一个车牌号码输入框
        if (inputView == null) return;
        inputView.performFirstFieldView();
    }

    @OnClick(R.id.btn_addcar)
    public void onViewClicked() {
        addCar();
    }

    private void addCar() {
        if (carNumber == null || carNumber.equals("")) {
            showToastMsgShort("请输入车牌号码");
            return;
        }
        CarMessage.Car car = new CarMessage.Car();
        car.setCar_power_tpye(carType); //是否为新能源
        car.setCar_license_plate(carNumber); //车牌号码
        car.setUser_id(app.getLoginUser().getData().getId()); //用户id
        Gson gson=new Gson();
        String carInfo=gson.toJson(car);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),carInfo);
        carMannagePresenter.addCar(body);
    }

    public class CheckBoxProxyImpl implements KeyboardInputController.LockNewEnergyProxy {
        private CheckBox checkBox;

        public CheckBoxProxyImpl(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        @Override
        public void setOnClickListener(View.OnClickListener listener) {
            checkBox.setOnClickListener(listener);
        }

        @Override
        public void onNumberTypeChanged(boolean isNewEnergyType) {
        }
    }

    @Override
    public void addCarSuccess(CommonBack commonBack) {
        if (commonBack == null) return;
        if (commonBack.getStatus() == 200) {
            showToastMsgShort("添加车辆成功");
            setResult(resultCode);
            finish();
        } else {
            showToastMsgShort("添加车辆失败：" + commonBack.getMessage());
        }
    }

    @Override
    public void addCarFailed() {
        showToastMsgShort("添加车辆失败");
    }

    @Override
    public void deleteCarSuccess(CommonBack commonBack) {

    }

    @Override
    public void deleteCarFailed() {

    }

    @Override
    public void updateCarSuccess(CommonBack commonBack) {

    }

    @Override
    public void getUserCarListSuccess(CarMessage carMessage) {
    }

    @Override
    public void getUserCarListFailed() {

    }


}
