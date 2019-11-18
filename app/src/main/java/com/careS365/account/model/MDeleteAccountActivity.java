package com.careS365.account.model;

import android.os.Message;

import com.careS365.account.presenter.IPDeleteAccountActivity;
import com.careS365.account.presenter.PDeleteAccountActivity;
import com.careS365.responseModel.DeleteAccountResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MDeleteAccountActivity implements IMDeleteAccountActivity {

    IPDeleteAccountActivity ipDeleteAccountActivity;

    public MDeleteAccountActivity(PDeleteAccountActivity pDeleteAccountActivity) {
        this.ipDeleteAccountActivity = pDeleteAccountActivity;
    }

    @Override
    public void deleteAccount(String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.deleteAccount(userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.DELETE_ACCOUNT_SUCCESS:
                    DeleteAccountResponseModel deleteAccountResponseModel = (DeleteAccountResponseModel) msg.obj;
                    ipDeleteAccountActivity.deleteAccountResponseSuccessFromModel(deleteAccountResponseModel);
                    break;
                case APIInterface.DELETE_ACCOUNT_FAILED:
                    String message = (String) msg.obj;
                    ipDeleteAccountActivity.deleteAccountResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
