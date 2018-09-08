package com.zykj.carfigure.mvp.view;

import com.zykj.carfigure.entity.Register;
import com.zykj.carfigure.entity.VerificationCode;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2210:54
 * desc   :
 * version: 1.0
 */
public interface IUserRegisterView {
    void getVerificationCodeSucess(VerificationCode code);
    void getVerificationCodeFailed();
    void registerSucess(Register register);
    void registerFailed();
    void resetPasswordSuccess(Register register);
    void resetPasswordFailed();

}
