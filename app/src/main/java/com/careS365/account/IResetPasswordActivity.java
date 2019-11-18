package com.careS365.account;

import com.careS365.responseModel.ResetPasswordResponseModel;

public interface IResetPasswordActivity {
    void resetPasswordSuccessFromPresenterToActivity(ResetPasswordResponseModel resetPasswordResponseModel);
    void resetPasswordFailureFromPresenterToActivity(String message);
}
