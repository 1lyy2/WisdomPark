package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.entity.ZyCloudStatus;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.ZyCloudModel;
import com.zykj.carfigure.mvp.view.IZyCloudVIew;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/617:40
 * desc   :
 * version: 1.0
 */
public class ZyCloudPresenter implements BaseIPresenter<IZyCloudVIew> {
    ZyCloudModel zyCloudModel;
    SoftReference<IZyCloudVIew> softReference;

    public ZyCloudPresenter(IZyCloudVIew softReference) {
        zyCloudModel = new ZyCloudModel();
        attech(softReference);
    }

    @Override
    public void attech(IZyCloudVIew view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void sendCommand(String devId, int status) {
        zyCloudModel.sendCommand(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().sendCommandSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().sendCommandFailed();
            }

            @Override
            public void onComplete() {

            }
        }, devId, status);
    }

    public void getCarParkingStatus(String devId) {
        zyCloudModel.getCarParkStatus(new Observer<ZyCloudStatus>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ZyCloudStatus status) {
                softReference.get().getCarParkStatusSuccess(status);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getCarParkStatusFailed();
            }

            @Override
            public void onComplete() {

            }
        }, devId);
    }
}
