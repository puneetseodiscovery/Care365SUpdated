package com.careS365.signup;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IMSignUpActivity {
    void signUp(String username, String phone, String password, String cnfrmPass,MultipartBody.Part image, RequestBody imgReq);
    void signUpWithoutImg(String username, String phone, String password, String cnfrmPass);
    void uploadImg(MultipartBody.Part userImg, RequestBody userImgName);
    void verifyOTP(String phone, String otp);
}
