package com.careS365.forgotpass;

import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;

public interface IForgotPasswordActivity {
    void onForgotPassSuccessFromPresenterToActivity(ForgotPasswordResponseModel forgotPasswordResponseModel);
    void onForgotPassFailureFromPresenterToActivity(String message);
    void onForgotPassVerifyOTPSuccessFromPresenterToActivity(ForgotPassVerifyOTPResponseModel forgotPassVerifyOTPResponseModel);
    void onForgotPassVerifyOTPFailureFromPresenterToActivity(String msgg);

    void onResendOtpSuccessResponseFromPrensenter(ResendOtpResponseModel resendOtpResponseModel);
    void onResendOtpFailedResponseFromPrensenter(String message);
}
