package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.ReservedParking;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.MyOrderParkingModel;
import com.zykj.carfigure.mvp.view.IMyOrderParkingView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/611:02
 * desc   :
 * version: 1.0
 */
public class MyOrderParkingPresenter implements BaseIPresenter<IMyOrderParkingView> {
    MyOrderParkingModel myOrderParkingModel;
    SoftReference<IMyOrderParkingView> softReference;

    public MyOrderParkingPresenter(IMyOrderParkingView softReference) {
        myOrderParkingModel = new MyOrderParkingModel();
        attech(softReference);
    }

    @Override
    public void attech(IMyOrderParkingView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void getMyOrderParkingList(int user_id, int order_statu) {
        myOrderParkingModel.getMyOrderParkingList(new Observer<ReservedParking>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ReservedParking reservedParking) {
                softReference.get().getMyOrderParkingListSuccess(reservedParking);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getMyOrderParkingListFailed();
            }

            @Override
            public void onComplete() {

            }
        }, user_id, order_statu);
    }
}
