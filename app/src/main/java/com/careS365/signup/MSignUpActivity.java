package com.careS365.signup;

import android.os.Message;

import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MSignUpActivity implements IMSignUpActivity {

    IPSignUpActivity ipSignUpActivity;

    public MSignUpActivity(PSignUpActivity pSignUpActivity) {
        ipSignUpActivity = pSignUpActivity;
    }

    /*@Override
    public void signUp(String username, String phone, String password, String cnfrmPass) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.signUp(username,phone,password,cnfrmPass,mHandler);
    }*/

    @Override
    public void signUp(String username, String phone, String password, String cnfrmPass, MultipartBody.Part image, RequestBody imgReq) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.signUp(username,phone,password,cnfrmPass,image,imgReq,mHandler);
    }

    @Override
    public void signUpWithoutImg(String username, String phone, String password, String cnfrmPass) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.signUpWithoutImg(username,phone,password,cnfrmPass,mHandler);
    }

    @Override
    public void uploadImg(MultipartBody.Part userImg, RequestBody userImgName) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.uploadImg(userImg,userImgName,mHandler);
    }

    @Override
    public void verifyOTP(String phone, String otp) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.verifyOTP(phone,otp,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.SIGNUP_SUCCESS:
                    SignUpResponseModel signUpResponseModel = (SignUpResponseModel) msg.obj;
                    ipSignUpActivity.signUpResponseSuccessFromModel(signUpResponseModel);
                    break;
                case APIInterface.SIGNUP_FAILED:
                    String message = (String) msg.obj;
                    ipSignUpActivity.signUpResponseFailureFromModel(message);
                    break;
                case APIInterface.VERIFY_OTP_SUCCESS:
                    VerifyOTPResponseModel verifyOTPResponseModel = (VerifyOTPResponseModel) msg.obj;
                    ipSignUpActivity.verifyOTPResponseSuccessFromModel(verifyOTPResponseModel);
                    break;
                case APIInterface.VERIFY_OTP_FAILED:
                    String msgg = (String) msg.obj;
                    ipSignUpActivity.verifyOTPResponseFailureFromModel(msgg);
                    break;
            }
        }
    };
}
