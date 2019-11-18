package com.careS365.contact;

import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;

public class PContactActivity implements IPContactActivity {

    IContactActivity iContactActivity;
    IMContactActivity imContactActivity;

    public PContactActivity(ContactActivity contactActivity) {
        iContactActivity = contactActivity;
        imContactActivity = new MContactActivity(this);
    }

    @Override
    public void getAdminEmail() {
        imContactActivity.getAdminEmail();
    }

    @Override
    public void getAdminEmailResponseSuccessFromModel(GetAdminEmailResponseModel getAdminEmailResponseModel) {
        iContactActivity.getAdminEmailSuccessFromPresenterToActivity(getAdminEmailResponseModel);
    }

    @Override
    public void getAdminEmailResponseFailureFromModel(String message) {
        iContactActivity.getAdminEmailFailureFromPresenterToActivity(message);
    }

    @Override
    public void contactUs(String email, String msg) {
        imContactActivity.contactUs(email,msg);
    }

    @Override
    public void contactUsResponseSuccessFromModel(ContactUsReponseModel contactUsReponseModel) {
        iContactActivity.contactUsSuccessFromPresenterToActivity(contactUsReponseModel);
    }

    @Override
    public void contactUsResponseFailureFromModel(String msgg) {
        iContactActivity.contactUsFailureFromPresenterToActivity(msgg);
    }
}
