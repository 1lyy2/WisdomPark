package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CarMessage;
import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.CarMannageModel;
import com.zykj.carfigure.mvp.view.ICarMannageView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/249:48
 * desc   :
 * version: 1.0
 */
public class CarMannagePresenter implements BaseIPresenter<ICarMannageView> {
    CarMannageModel carMannageModel;
    private SoftReference<ICarMannageView> softReference;

    public CarMannagePresenter(ICarMannageView softReference) {
        carMannageModel = new CarMannageModel();
        attech(softReference);
    }

    @Override
    public void attech(ICarMannageView view) {
        softReference = new SoftReference<ICarMannageView>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    //添加车辆
    public void addCar(RequestBody requestBody) {
        carMannageModel.addCar(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().addCarSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().addCarFailed();
            }

            @Override
            public void onComplete() {

            }
        }, requestBody);
    }

    //获取当前用户的车辆信息
    public void getUserCar(int user_id) {
        carMannageModel.getUserCarList(new Observer<CarMessage>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CarMessage carMessage) {
                softReference.get().getUserCarListSuccess(carMessage);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getUserCarListFailed();
            }

            @Override
            public void onComplete() {

            }
        }, user_id);
    }

    //删除当前用户的车辆信息
    public void deleteUserCar(int car_id) {
        carMannageModel.deleteCar(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().deleteCarSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().deleteCarFailed();
            }

            @Override
            public void onComplete() {

            }
        }, car_id);
    }


}
