package com.careS365.account.model;

import android.os.Message;

import com.careS365.account.presenter.IPEditUserNameActivity;
import com.careS365.account.presenter.PEditUserNameActivity;
import com.careS365.responseModel.EditUsernameResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MEditUserNameActivity implements IMEditUserNameActivity {

    IPEditUserNameActivity ipEditUserNameActivity;

    public MEditUserNameActivity(PEditUserNameActivity pEditUserNameActivity) {
        this.ipEditUserNameActivity = pEditUserNameActivity;
    }

    @Override
    public void editUsername(String username,String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.editUsername(username,userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.EDIT_USERNAME_SUCCESS:
                    EditUsernameResponseModel editUsernameResponseModel = (EditUsernameResponseModel) msg.obj;
                    ipEditUserNameActivity.editUsernameResponseSuccessFromModel(editUsernameResponseModel);
                    break;
                case APIInterface.EDIT_USERNAME_FAILED:
                    String message = (String) msg.obj;
                    ipEditUserNameActivity.editUsernameResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
