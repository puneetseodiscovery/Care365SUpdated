package com.careS365.signup;

import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

public interface ISignUpActivity {
    void onSignUpSuccessFromPresenterToActivity(SignUpResponseModel signUpResponseModel);
    void onSignUpFailureFromPresenterToActivity(String message);
    void verifyOTPSuccessFromPresenterToActivity(VerifyOTPResponseModel verifyOTPResponseModel);
    void verifyOTPFailureFromPresenterToActivity(String msgg);
}
