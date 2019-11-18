package com.careS365.login;

import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

public class PLoginActivity implements IPLoginActivity {

    ILoginActivity iLoginActivity;
    IMLoginActivity imLoginActivity;

    public PLoginActivity(LoginActivity loginActivity) {
        iLoginActivity = loginActivity;

        /*Error :  Model wrong init*/
       // imLoginActivity = new MLoginActivity(this);
    }

    @Override
    public void login(String phone, String password, String device_token) {

        imLoginActivity=new MLoginActivity(this);
        imLoginActivity.login(phone,password,device_token);

    }

    @Override
    public void loginResponseSuccessFromModel(LoginResponseModel loginResponseModel) {
        iLoginActivity.loginSuccessFromPresenterToActivity(loginResponseModel);
    }

    @Override
    public void loginResponseFailureFromModel(String message) {

        iLoginActivity.loginFailureFromPresenterToActivity(message);
    }

    @Override
    public void loginOTPNotVerifiedResponseFailureFromModel(String msgg) {
        iLoginActivity.loginOTPNotVerifiedFailureFromPresenterToActivity(msgg);
    }

    @Override
    public void verifyOTP(String phoneNum, String otp) {
        imLoginActivity.verifyOTP(phoneNum,otp);
    }

    @Override
    public void verifyOTPResponseSuccessFromModel(VerifyOTPResponseModel verifyOTPResponseModel) {
        iLoginActivity.verifyOTPSuccessFromPresenterToActivity(verifyOTPResponseModel);
    }

    @Override
    public void verifyOTPResponseFailureFromModel(String msggg) {
        iLoginActivity.verifyOTPFailureFromPresenterToActivity(msggg);
    }
}
