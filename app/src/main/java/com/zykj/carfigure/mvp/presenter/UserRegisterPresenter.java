package com.zykj.carfigure.mvp.presenter;

import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.VerificationCode;
import com.zykj.carfigure.mvp.BaseIPresenter;
import com.zykj.carfigure.mvp.model.UserRegisterModel;
import com.zykj.carfigure.mvp.view.IUserRegisterView;

import java.lang.ref.SoftReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2210:48
 * desc   : 注册presenter
 * version: 1.0
 */
public class UserRegisterPresenter implements BaseIPresenter<IUserRegisterView> {
    UserRegisterModel userRegisterModel;
    //使用软引用
    private SoftReference<IUserRegisterView> softReference;

    public UserRegisterPresenter(IUserRegisterView softReference) {
        userRegisterModel = new UserRegisterModel();
        attech(softReference);
    }

    @Override
    public void attech(IUserRegisterView view) {
        softReference = new SoftReference<IUserRegisterView>(view);
    }

    @Override
    public void detech() {
        softReference.clear();
    }

    //获取验证码
    public void getVerificationCode(String phone) {
        userRegisterModel.getVerificationCode(new Observer<VerificationCode>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(VerificationCode s) {
                softReference.get().getVerificationCodeSucess(s);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().getVerificationCodeFailed();
            }

            @Override
            public void onComplete() {

            }
        }, phone);
    }

    //用户注册
    public void registerUser(String username, String password, String code) {
        userRegisterModel.register(new Observer<Register>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Register register) {
                softReference.get().registerSucess(register);
            }

            @Override
            public void onError(Throwable e) {
                softReference.get().registerFailed();
            }

            @Override
            public void onComplete() {

            }
        }, username, password, code);
    }

    public void resetPassword(String username,String code){
        userRegisterModel.resetPassword(new Observer<Register>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Register register) {
                   softReference.get().resetPasswordSuccess(register);
            }

            @Override
            public void onError(Throwable e) {
                   softReference.get().resetPasswordFailed();
            }

            @Override
            public void onComplete() {

            }
        },username,code);
    }

}
