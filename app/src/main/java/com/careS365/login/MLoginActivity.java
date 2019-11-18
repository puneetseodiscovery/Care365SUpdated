package com.careS365.login;

import android.os.Handler;
import android.os.Message;

import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MLoginActivity implements IMLoginActivity {

    IPLoginActivity ipLoginActivity;

    public MLoginActivity(PLoginActivity pLoginActivity) {
        this.ipLoginActivity = pLoginActivity;
    }

    @Override
    public void login(String phone, String password, String device_token) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.login(phone,password,device_token,mHandler);
    }

    @Override
    public void verifyOTP(String phoneNum, String otp) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.verifyOTP(phoneNum,otp,mHandler);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIInterface.LOGIN_SUCCESS:
                    LoginResponseModel loginResponseModel = (LoginResponseModel) msg.obj;
                    ipLoginActivity.loginResponseSuccessFromModel(loginResponseModel);
                    break;
                case APIInterface.LOGIN_FAILED:
                    String message = String.valueOf(msg.obj);
                    ipLoginActivity.loginResponseFailureFromModel(message);
                    break;
                case APIInterface.LOGIN_OTP_NOT_VERIFIED:
                    String msgg = (String) msg.obj;
                    ipLoginActivity.loginOTPNotVerifiedResponseFailureFromModel(msgg);
                    break;
                case APIInterface.VERIFY_OTP_SUCCESS:
                    VerifyOTPResponseModel verifyOTPResponseModel = (VerifyOTPResponseModel) msg.obj;
                    ipLoginActivity.verifyOTPResponseSuccessFromModel(verifyOTPResponseModel);
                    break;
                case APIInterface.VERIFY_OTP_FAILED:
                    String msggg = (String) msg.obj;
                    ipLoginActivity.verifyOTPResponseFailureFromModel(msggg);
                    break;
            }
        }
    };
}
