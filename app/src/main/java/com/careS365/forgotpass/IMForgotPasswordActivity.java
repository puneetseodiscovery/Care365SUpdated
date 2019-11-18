package com.careS365.forgotpass;

public interface IMForgotPasswordActivity {
    void forgotPass(String mobile,String email);
    void forgotPassVerifyOTP(String mobile, String otp);
    void sendOtpRestCalls(String phone_num);
}
