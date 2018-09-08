package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.ParkingRecord;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.ParkingRecordModel;
import com.zykj.carfigure.mvp.view.IParkingRecordView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/716:59
 * desc   : 停车记录
 * version: 1.0
 */
public class ParkingRecordPresenter implements BaseIPresenter<IParkingRecordView> {
    ParkingRecordModel parkingRecordModel;
    SoftReference<IParkingRecordView> softReference;

    public ParkingRecordPresenter(IParkingRecordView softReference) {
        parkingRecordModel = new ParkingRecordModel();
        attech(softReference);
    }

    @Override
    public void attech(IParkingRecordView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void getParkingRecordList(int user_id) {
        parkingRecordModel.getParkingRecordList(new Observer<ParkingRecord>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ParkingRecord parkingRecord) {
                softReference.get().getParkingRecordListSuccess(parkingRecord);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getParkingRecordListFailed();
            }

            @Override
            public void onComplete() {

            }
        }, user_id);
    }

}
