package com.careS365.signup;

import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PSignUpActivity implements IPSignUpActivity {

    ISignUpActivity iSignUpActivity;
    IMSignUpActivity imSignUpActivity;

    public PSignUpActivity(SignUpActivity signUpActivity) {
        iSignUpActivity = signUpActivity;
        imSignUpActivity = new MSignUpActivity(this);
    }

    /*@Override
    public void signUp(String username, String phone, String password, String cnfrmPass) {
        imSignUpActivity.signUp(username,phone,password,cnfrmPass);
    }*/

    @Override
    public void uploadImg(MultipartBody.Part userImg, RequestBody userImgName) {
        imSignUpActivity.uploadImg(userImg,userImgName);
    }

    @Override
    public void verifyOTP(String phone, String otp) {
        imSignUpActivity.verifyOTP(phone,otp);
    }

    @Override
    public void verifyOTPResponseSuccessFromModel(VerifyOTPResponseModel verifyOTPResponseModel) {
        iSignUpActivity.verifyOTPSuccessFromPresenterToActivity(verifyOTPResponseModel);
    }

    @Override
    public void verifyOTPResponseFailureFromModel(String msgg) {
        iSignUpActivity.verifyOTPFailureFromPresenterToActivity(msgg);
    }

    @Override
    public void signUp(String username, String phone, String password, String cnfrmPass, MultipartBody.Part image, RequestBody imgReq) {
        imSignUpActivity.signUp(username,phone,password,cnfrmPass,image,imgReq);
    }

    @Override
    public void signUpWithoutImg(String username, String phone, String password, String cnfrmPass) {
        imSignUpActivity.signUpWithoutImg(username,phone,password,cnfrmPass);
    }

    @Override
    public void signUpResponseSuccessFromModel(SignUpResponseModel signUpResponseModel) {
        iSignUpActivity.onSignUpSuccessFromPresenterToActivity(signUpResponseModel);
    }

    @Override
    public void signUpResponseFailureFromModel(String message) {
        iSignUpActivity.onSignUpFailureFromPresenterToActivity(message);
    }


}
