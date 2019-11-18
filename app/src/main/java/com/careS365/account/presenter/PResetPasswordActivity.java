package com.careS365.account.presenter;

import com.careS365.account.IResetPasswordActivity;
import com.careS365.account.ResetPasswordActivity;
import com.careS365.account.model.IMResetPasswordActivity;
import com.careS365.account.model.MResetPasswordActivity;
import com.careS365.responseModel.ResetPasswordResponseModel;

public class PResetPasswordActivity implements IPResetPasswordActivity {

    IResetPasswordActivity iResetPasswordActivity;
    IMResetPasswordActivity imResetPasswordActivity;

    public PResetPasswordActivity(ResetPasswordActivity resetPasswordActivity) {
        iResetPasswordActivity = resetPasswordActivity;
        imResetPasswordActivity = new MResetPasswordActivity(this);
    }

    @Override
    public void resetPassword(String newPass, String cnfrmPass, String userId) {
        imResetPasswordActivity.resetPassword(newPass,cnfrmPass,userId);
    }

    @Override
    public void resetPasswordResponseSuccessFromModel(ResetPasswordResponseModel resetPasswordResponseModel) {
        iResetPasswordActivity.resetPasswordSuccessFromPresenterToActivity(resetPasswordResponseModel);
    }

    @Override
    public void resetPasswordResponseFailureFromModel(String message) {
        iResetPasswordActivity.resetPasswordFailureFromPresenterToActivity(message);
    }
}
