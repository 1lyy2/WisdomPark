package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.Street;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.StreetModel;
import com.zykj.carfigure.mvp.view.IStreetView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2815:58
 * desc   : 街道控制器
 * version: 1.0
 */
public class StreetPresenter implements BaseIPresenter<IStreetView> {
    StreetModel streetModel;
    private SoftReference<IStreetView> softReference;

    public StreetPresenter(IStreetView softReference) {
        streetModel = new StreetModel();
        attech(softReference);
    }

    @Override
    public void attech(IStreetView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    /**
     * 获取附近街道列表数据
     *
     * @param longitude
     * @param latitude
     * @param scopen
     */
    public void getStreetList(double latitude, double longitude, final int scopen) {
        streetModel.getStreetList(new Observer<Street>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Street street) {
                softReference.get().getStreetListSuccess(street);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getStreetListFailed();
            }

            @Override
            public void onComplete() {

            }
        }, latitude, longitude, scopen);
    }

}
