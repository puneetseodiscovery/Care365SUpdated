package com.careS365.contact;

import android.os.Message;

import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MContactActivity implements IMContactActivity {

    IPContactActivity ipContactActivity;

    public MContactActivity(PContactActivity pContactActivity) {
        this.ipContactActivity = pContactActivity;
    }

    @Override
    public void getAdminEmail() {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.getAdminEmail(mHandler);
    }

    @Override
    public void contactUs(String email, String msg) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.contactUs(email,msg,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.GET_ADMIN_EMAIL_SUCCESS:
                    GetAdminEmailResponseModel getAdminEmailResponseModel = (GetAdminEmailResponseModel) msg.obj;
                    ipContactActivity.getAdminEmailResponseSuccessFromModel(getAdminEmailResponseModel);
                    break;
                case APIInterface.GET_ADMIN_EMAIL_FAILED:
                    String message = (String) msg.obj;
                    ipContactActivity.getAdminEmailResponseFailureFromModel(message);
                    break;
                case APIInterface.CONTACT_US_SUCCESS:
                    ContactUsReponseModel contactUsReponseModel = (ContactUsReponseModel) msg.obj;
                    ipContactActivity.contactUsResponseSuccessFromModel(contactUsReponseModel);
                    break;
                case APIInterface.CONTACT_US_FAILED:
                    String msgg = (String) msg.obj;
                    ipContactActivity.contactUsResponseFailureFromModel(msgg);
                    break;
            }
        }
    };
}
