package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CarPark;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.CarParkModel;
import com.zykj.carfigure.mvp.view.ICarParkView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3014:50
 * desc   : 车位控制器
 * version: 1.0
 */
public class CarParkPresenter implements BaseIPresenter<ICarParkView> {
    CarParkModel carParkModel;
    private SoftReference<ICarParkView> softReference;

    public CarParkPresenter(ICarParkView softReference) {
        carParkModel = new CarParkModel();
        attech(softReference);
    }

    @Override
    public void attech(ICarParkView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void getFreeCarParkList( int streetId,int state) {
        carParkModel.getFreeCarParkList(new Observer<CarPark>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CarPark carPark) {
                softReference.get().getFreeCarParkListSuccess(carPark);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getFreeCarParkListFailed();
            }

            @Override
            public void onComplete() {

            }
        }, streetId,state);
    }
}
