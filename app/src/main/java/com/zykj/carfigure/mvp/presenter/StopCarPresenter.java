package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.NearCarPark;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.StopCarModel;
import com.zykj.carfigure.mvp.view.IStopCarView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/410:26
 * desc   : 停车控制器
 * version: 1.0
 */
public class StopCarPresenter implements BaseIPresenter<IStopCarView> {
    StopCarModel stopCarModel;
    private SoftReference<IStopCarView> softReference;

    public StopCarPresenter(IStopCarView softReference) {
        stopCarModel = new StopCarModel();
        attech(softReference);
    }

    @Override
    public void attech(IStopCarView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    //查找当前位置最近的车位信息
    public void findNearCarPark(double userLatitude, double userLongitude) {
        stopCarModel.findNearCarPark(new Observer<NearCarPark>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NearCarPark nearCarPark) {
                softReference.get().findNearCarParkSuccess(nearCarPark);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().findNearCarParkFailed();
            }

            @Override
            public void onComplete() {

            }
        }, userLatitude, userLongitude);
    }

    //我要停车
    public void stopCar(int user_id, String carParkNumber, String licensePlateNumber) {
        stopCarModel.stopCar(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().stopCarSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().stopCarFailed();
            }

            @Override
            public void onComplete() {

            }
        }, user_id, carParkNumber, licensePlateNumber);
    }

}
