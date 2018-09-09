package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.Bills;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.BillsModel;
import com.zykj.carfigure.mvp.view.IBillsView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/817:05
 * desc   :
 * version: 1.0
 */
public class BillsPresenter implements BaseIPresenter<IBillsView> {
    BillsModel billsModel;
    SoftReference<IBillsView> softReference;

    public BillsPresenter(IBillsView softReference) {
        billsModel = new BillsModel();
        attech(softReference);
    }

    @Override
    public void attech(IBillsView view) {
        softReference = new SoftReference<>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    public void getUserBills(int state) {
        billsModel.getUserBills(new Observer<Bills>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bills bills) {
                softReference.get().getUserBillsSuccess(bills);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getUserBillsFailed();
            }

            @Override
            public void onComplete() {

            }
        }, state);
    }
}
