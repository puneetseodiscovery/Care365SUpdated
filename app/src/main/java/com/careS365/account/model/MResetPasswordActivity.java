package com.careS365.account.model;

import android.os.Message;

import com.careS365.account.presenter.IPResetPasswordActivity;
import com.careS365.account.presenter.PResetPasswordActivity;
import com.careS365.responseModel.ResetPasswordResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MResetPasswordActivity implements IMResetPasswordActivity {

    IPResetPasswordActivity ipResetPasswordActivity;

    public MResetPasswordActivity(PResetPasswordActivity pResetPasswordActivity) {
        this.ipResetPasswordActivity = pResetPasswordActivity;
    }

    @Override
    public void resetPassword(String newPass, String cnfrmPass, String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.resetPassword(newPass,cnfrmPass,userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.RESET_PASSWORD_SUCCESS:
                    ResetPasswordResponseModel resetPasswordResponseModel = (ResetPasswordResponseModel) msg.obj;
                    ipResetPasswordActivity.resetPasswordResponseSuccessFromModel(resetPasswordResponseModel);
                    break;
                case APIInterface.RESET_PASSWORD_FAILED:
                    String message = (String) msg.obj;
                    ipResetPasswordActivity.resetPasswordResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
