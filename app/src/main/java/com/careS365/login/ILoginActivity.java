package com.careS365.login;

import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

public interface ILoginActivity {

    void loginSuccessFromPresenterToActivity(LoginResponseModel loginResponseModel);
    void loginFailureFromPresenterToActivity(String message);
    void loginOTPNotVerifiedFailureFromPresenterToActivity(String msgg);

    void verifyOTPSuccessFromPresenterToActivity(VerifyOTPResponseModel verifyOTPResponseModel);
    void verifyOTPFailureFromPresenterToActivity(String msggg);
}
