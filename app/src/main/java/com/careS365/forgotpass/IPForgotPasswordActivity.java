package com.careS365.forgotpass;

import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;

public interface IPForgotPasswordActivity {
    void forgotPass(String mobile,String email);
    void forgotPassResponseSuccessFromModel(ForgotPasswordResponseModel forgotPasswordResponseModel);
    void forgotPassResponseFailureFromModel(String message);

    void forgotPassVerifyOTP(String mobile, String otp);
    void forgotPassVerifyOTPResponseSuccessFromModel(ForgotPassVerifyOTPResponseModel forgotPassVerifyOTPResponseModel);
    void forgotPassVerifyOTPResponseFailureFromModel(String msgg);

    void resendOtp(String phone_num);
    void onResendOtpSuccessResponseFromModel(ResendOtpResponseModel resendOtpResponseModel);
    void onResendOtpFailedResponseFromModel(String message);
}
