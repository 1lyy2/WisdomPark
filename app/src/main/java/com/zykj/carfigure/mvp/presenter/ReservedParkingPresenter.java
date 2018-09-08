package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.ReservedParkingModel;
import com.zykj.carfigure.mvp.view.IReservedParkingView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/514:21
 * desc   : 预约车位
 * version: 1.0
 */
public class ReservedParkingPresenter implements BaseIPresenter<IReservedParkingView> {
    ReservedParkingModel reservedParkingModel;
    SoftReference<IReservedParkingView> softReference;

    public ReservedParkingPresenter(IReservedParkingView softReference) {
        reservedParkingModel = new ReservedParkingModel();
        attech(softReference);
    }

    @Override
    public void attech(IReservedParkingView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void reservedParking(int user_id, String licensePlateNumber, final int street_id, String parkNumber, String order_time, String linkman, String linkPhone,String streetName) {
        reservedParkingModel.reservedParking(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().reservedParkingSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().reservedParkingFailed();
            }

            @Override
            public void onComplete() {
            }
        }, user_id, licensePlateNumber, street_id, parkNumber, order_time, linkman, linkPhone,streetName);
    }
}
