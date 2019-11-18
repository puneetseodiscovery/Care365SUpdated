package com.careS365.forgotpass;

import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;

public class PForgotPasswordActivity implements IPForgotPasswordActivity {

    IForgotPasswordActivity iForgotPasswordActivity;
    IMForgotPasswordActivity imForgotPasswordActivity;

    public PForgotPasswordActivity(ForgotPasswordActivity forgotPasswordActivity) {
        iForgotPasswordActivity = forgotPasswordActivity;
        imForgotPasswordActivity = new MForgotPasswordActivity(this);
    }

    @Override
    public void forgotPass(String mobile,String email) {
        imForgotPasswordActivity.forgotPass(mobile,email);
    }

    @Override
    public void forgotPassVerifyOTP(String mobile, String otp) {
        imForgotPasswordActivity.forgotPassVerifyOTP(mobile, otp);
    }

    @Override
    public void forgotPassVerifyOTPResponseSuccessFromModel(ForgotPassVerifyOTPResponseModel forgotPassVerifyOTPResponseModel) {
        iForgotPasswordActivity.onForgotPassVerifyOTPSuccessFromPresenterToActivity(forgotPassVerifyOTPResponseModel);
    }

    @Override
    public void forgotPassVerifyOTPResponseFailureFromModel(String msgg) {
        iForgotPasswordActivity.onForgotPassVerifyOTPFailureFromPresenterToActivity(msgg);
    }

    @Override
    public void resendOtp(String phone_num) {
        imForgotPasswordActivity.sendOtpRestCalls(phone_num);
    }

    @Override
    public void onResendOtpSuccessResponseFromModel(ResendOtpResponseModel resendOtpResponseModel) {
        iForgotPasswordActivity.onResendOtpSuccessResponseFromPrensenter(resendOtpResponseModel);
    }

    @Override
    public void onResendOtpFailedResponseFromModel(String message) {
        iForgotPasswordActivity.onResendOtpFailedResponseFromPrensenter(message);
    }

    @Override
    public void forgotPassResponseSuccessFromModel(ForgotPasswordResponseModel forgotPasswordResponseModel) {
        iForgotPasswordActivity.onForgotPassSuccessFromPresenterToActivity(forgotPasswordResponseModel);
    }

    @Override
    public void forgotPassResponseFailureFromModel(String message) {
        iForgotPasswordActivity.onForgotPassFailureFromPresenterToActivity(message);
    }


}
