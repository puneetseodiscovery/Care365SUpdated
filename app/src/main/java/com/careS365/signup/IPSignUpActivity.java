package com.careS365.signup;

import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IPSignUpActivity {
    void signUp(String username, String phone, String password, String cnfrmPass , MultipartBody.Part image, RequestBody imgReq);
    void signUpWithoutImg(String username, String phone, String password, String cnfrmPass);
    void signUpResponseSuccessFromModel(SignUpResponseModel signUpResponseModel);
    void signUpResponseFailureFromModel(String message);
    void uploadImg(MultipartBody.Part userImg, RequestBody userImgName);
    void verifyOTP(String phone, String otp);
    void verifyOTPResponseSuccessFromModel(VerifyOTPResponseModel verifyOTPResponseModel);
    void verifyOTPResponseFailureFromModel(String msgg);
}
