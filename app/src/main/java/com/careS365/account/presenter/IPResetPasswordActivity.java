package com.careS365.account.presenter;

import com.careS365.responseModel.ResetPasswordResponseModel;

public interface IPResetPasswordActivity {
    void resetPassword(String newPass, String cnfrmPass, String userId);
    void resetPasswordResponseSuccessFromModel(ResetPasswordResponseModel resetPasswordResponseModel);
    void resetPasswordResponseFailureFromModel(String message);
}
