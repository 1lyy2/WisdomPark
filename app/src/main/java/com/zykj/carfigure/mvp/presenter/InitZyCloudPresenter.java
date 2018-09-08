package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.CommonBack;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.InitZyCloudModel;
import com.zykj.carfigure.mvp.view.IInitZyCloudView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/710:33
 * desc   :
 * version: 1.0
 */
public class InitZyCloudPresenter implements BaseIPresenter<IInitZyCloudView> {
    InitZyCloudModel initZyCloudModel;
    SoftReference<IInitZyCloudView> softReference;

    public InitZyCloudPresenter(IInitZyCloudView softReference) {
        initZyCloudModel = new InitZyCloudModel();
        attech(softReference);

    }

    @Override
    public void attech(IInitZyCloudView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void initZyCloud(String devId, int type) {
        initZyCloudModel.initZyCloud(new Observer<CommonBack>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CommonBack commonBack) {
                softReference.get().initZyCloudSuccess(commonBack);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().initZyCloudFailed();
            }

            @Override
            public void onComplete() {

            }
        }, devId, type);
    }
}
