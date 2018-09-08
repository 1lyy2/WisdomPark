package com.zykj.carfigure.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.carfigure.R;
import com.zykj.carfigure.adapter.MyCarAdapter;
import com.zykj.carfigure.adapter.base.BaseRecylerAdapter;
import com.zykj.carfigure.app.Constants;
import com.zykj.carfigure.base.UserBaseActivity;
import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.User;
import com.zykj.carfigure.eventbus.BindEventBus;
import com.zykj.carfigure.eventbus.Event;
import com.zykj.carfigure.eventbus.EventCode;
import com.zykj.carfigure.mvp.presenter.CarMannagePresenter;
import com.zykj.carfigure.mvp.view.ICarMannageView;
import com.zykj.carfigure.widget.EmptyRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//我的车辆
@BindEventBus
public class MyCarActivity extends UserBaseActivity implements ICarMannageView ,BaseRecylerAdapter.OnItemClickListener {

    @BindView(R.id.myCarRecyclerView)
    EmptyRecyclerView myCarRecyclerView;
    @BindView(R.id.btn_bindcar)
    Button btnBindcar;
    @BindView(R.id.lin_empty_macar)
    LinearLayout linEmptyMacar;
    @BindView(R.id.common_right_text)
    TextView commonRightText;
    private MyCarAdapter myCarAdapter;
    private CarMannagePresenter carMannagePresenter;
    private int type = 1;//1为正常，2为选择

    @Override
    public void onCreatePresenter() {
        carMannagePresenter = new CarMannagePresenter(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_car;
    }

    @Override
    protected void initView() {
        enableSupportActionBar();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        init();
        if (Constants.user == null) return;
        User.DataBean user = Constants.user.getData();
        if (user == null) {

        } else {
            showLoadingView(null, null);
            carMannagePresenter.getUserCar(user.getId());
        }

    }

    @Override
    protected String getActivityName() {
        return getString(R.string.my_car_string);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void init() {
        commonRightText.setText("添加车辆");
        if(type==2){
            commonRightText.setVisibility(View.GONE);
        }else{
            commonRightText.setVisibility(View.VISIBLE);
        }
        commonRightText.setTextColor(getResources().getColor(R.color.common_title_text));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myCarRecyclerView.setLayoutManager(linearLayoutManager);
        myCarRecyclerView.setEmptyView(linEmptyMacar);
        myCarAdapter = new MyCarAdapter(this, type);
        myCarAdapter.setOnItemClickListener(this);
        myCarRecyclerView.setAdapter(myCarAdapter);
    }

    @OnClick({R.id.common_right_text, R.id.btn_bindcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_right_text:
                //添加车辆
            case R.id.btn_bindcar:
                //绑定车辆
                Intent intent = new Intent();
                intent.setClass(this, AddCarActivity.class);
                startActivityForResult(intent, 1200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AddCarActivity.resultCode) {
            if (carMannagePresenter != null) {
                carMannagePresenter.getUserCar(Constants.user.getData().getId());
            }
        }
    }

    @Override
    public void addCarSuccess(CommonBack commonBack) {

    }

    @Override
    public void addCarFailed() {

    }

    @Override
    public void deleteCarSuccess(CommonBack commonBack) {
        hideLoadingView();
        if (commonBack == null) return;
        if (commonBack.getStatus() == 200) {
            carMannagePresenter.getUserCar(Constants.user.getData().getId());
            showToastMsgShort(getString(R.string.delete_car_success));
        } else {
            showToastMsgShort(getString(R.string.delet_car_failed));
        }
    }

    @Override
    public void deleteCarFailed() {
        hideLoadingView();
        showToastMsgShort(getString(R.string.delet_car_failed));
    }

    @Override
    public void updateCarSuccess(CommonBack commonBack) {

    }

    @Override
    public void getUserCarListSuccess(CarMessage carMessage) {
        hideLoadingView();
        if (carMessage == null) return;
        if (carMessage.getStatus() == 200) {
            List<CarMessage.Car> data = carMessage.getData();
            if (data == null) {
                showToastMsgShort("获取车辆信息失败");
            } else {
                if (myCarAdapter != null) {
                    myCarAdapter.setList(data);
                }
            }
        }

    }

    @Override
    public void getUserCarListFailed() {
        hideLoadingView();
        showToastMsgShort("获取车辆信息失败");
    }

    /**
     * 接受eventbus 适配器的click事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(Event<Object> event) {
        int code = event.getCode();
        switch (code) {
            case EventCode.DELETECAR:
                CarMessage.Car car = (CarMessage.Car) event.getData();
                if (car == null) return;
                int id = car.getId();
                showLoadingView(null, null);
                carMannagePresenter.deleteUserCar(id);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        CarMessage.Car car = (CarMessage.Car) item;
       if(type==2){
           myCarAdapter.setSelection(position);
           Intent intent  = new Intent();
           intent.putExtra("car",car);
           setResult(Constants.SELECTCARNUMBERCODE,intent);
           finish();
       }
    }
}
