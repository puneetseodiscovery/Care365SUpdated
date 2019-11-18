package com.careS365.login;

import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

public interface IPLoginActivity {

    void login(String phone, String password, String device_token);
    void loginResponseSuccessFromModel(LoginResponseModel loginResponseModel);
    void loginResponseFailureFromModel(String message);

    void loginOTPNotVerifiedResponseFailureFromModel(String msgg);
    void verifyOTP(String phoneNum, String otp);
    void verifyOTPResponseSuccessFromModel(VerifyOTPResponseModel verifyOTPResponseModel);
    void verifyOTPResponseFailureFromModel(String msggg);
}
