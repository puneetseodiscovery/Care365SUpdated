package com.careS365.account.model;

import android.os.Message;

import com.careS365.account.presenter.IPEditPhoneNumberActivity;
import com.careS365.account.presenter.PEditPhoneNumberActivity;
import com.careS365.responseModel.EditPhoneResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MEditPhoneNumberActivity implements IMEditPhoneNumberActivity {

    IPEditPhoneNumberActivity ipEditPhoneNumberActivity;

    public MEditPhoneNumberActivity(PEditPhoneNumberActivity pEditPhoneNumberActivity) {
        this.ipEditPhoneNumberActivity = pEditPhoneNumberActivity;
    }

    @Override
    public void editPhone(String phone, String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.editPhone(phone,userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.EDIT_PHONE_SUCCESS:
                    EditPhoneResponseModel editPhoneResponseModel = (EditPhoneResponseModel) msg.obj;
                    ipEditPhoneNumberActivity.editPhoneResponseSuccessFromModel(editPhoneResponseModel);
                    break;
                case APIInterface.EDIT_PHONE_FAILED:
                    String message = (String) msg.obj;
                    ipEditPhoneNumberActivity.editPhoneResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
