package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.Banner;
import com.zykj.carfigure.entity.Function;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.IndexModel;
import com.zykj.carfigure.mvp.view.IIndexView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2314:42
 * desc   : 首页控制器
 * version: 1.0
 */
public class IndexPresenter implements BaseIPresenter<IIndexView> {
    private IndexModel indexModel;
    //使用软引用
    private SoftReference<IIndexView> softReference;

    public IndexPresenter(IIndexView softReference) {
        indexModel = new IndexModel();
        attech(softReference);
    }

    @Override
    public void attech(IIndexView view) {
        softReference = new SoftReference<IIndexView>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    //获取轮播图
    public void getBannerList() {
        indexModel.getBannerList(new Observer<Banner>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Banner banner) {
                softReference.get().getBannerListSuccess(banner);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getBannerListFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //获取首页功能列表
    public void getFunctionList() {
        indexModel.getNavitionList(new Observer<Function>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Function function) {
                softReference.get().getFunctionListSuccess(function);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getFunctionListFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
