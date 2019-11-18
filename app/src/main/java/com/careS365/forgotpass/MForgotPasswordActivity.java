package com.careS365.forgotpass;

import android.os.Message;

import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MForgotPasswordActivity implements IMForgotPasswordActivity {

    IPForgotPasswordActivity ipForgotPasswordActivity;

    public MForgotPasswordActivity(PForgotPasswordActivity pForgotPasswordActivity) {
        this.ipForgotPasswordActivity = pForgotPasswordActivity;
    }

    @Override
    public void forgotPass(String mobile,String email) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.forgotPass(mobile,email, mHandler);
    }

    @Override
    public void forgotPassVerifyOTP(String mobile, String otp) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.forgotPassVerifyOTP(mobile, otp, mHandler);
    }

    @Override
    public void sendOtpRestCalls(String phone_num) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.resendApi(phone_num, mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.FORGOT_PASS_SUCCESS:
                    ForgotPasswordResponseModel forgotPasswordResponseModel = (ForgotPasswordResponseModel) msg.obj;
                    ipForgotPasswordActivity.forgotPassResponseSuccessFromModel(forgotPasswordResponseModel);
                    break;
                case APIInterface.FORGOT_PASS_FAILED:
                    String message = (String) msg.obj;
                    ipForgotPasswordActivity.forgotPassResponseFailureFromModel(message);
                    break;
                case APIInterface.FORGOT_PASS_VERIFY_OTP_SUCCESS:
                    ForgotPassVerifyOTPResponseModel forgotPassVerifyOTPResponseModel = (ForgotPassVerifyOTPResponseModel) msg.obj;
                    ipForgotPasswordActivity.forgotPassVerifyOTPResponseSuccessFromModel(forgotPassVerifyOTPResponseModel);
                    break;
                case APIInterface.FORGOT_PASS_VERIFY_OTP_FAILED:
                    String msgg = (String) msg.obj;
                    ipForgotPasswordActivity.forgotPassVerifyOTPResponseFailureFromModel(msgg);
                    break;
                case APIInterface.RESEND_OTP_SUCCESS:
                    ResendOtpResponseModel resendOtpResponseModel = (ResendOtpResponseModel) msg.obj;
                    ipForgotPasswordActivity.onResendOtpSuccessResponseFromModel(resendOtpResponseModel);
                    break;
                case APIInterface.RESEND_OTP_FAILD:
                    String failed = (String) msg.obj;
                    ipForgotPasswordActivity.onResendOtpFailedResponseFromModel(failed);
                    break;

            }
        }
    };
}
