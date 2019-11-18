package com.careS365.login;

public interface IMLoginActivity {

    void login(String phone, String password, String device_token);

    void verifyOTP(String phoneNum, String otp);
}
