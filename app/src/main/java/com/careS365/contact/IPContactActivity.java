package com.careS365.contact;

import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;

public interface IPContactActivity {
    void getAdminEmail();
    void getAdminEmailResponseSuccessFromModel(GetAdminEmailResponseModel getAdminEmailResponseModel);
    void getAdminEmailResponseFailureFromModel(String message);
    void contactUs(String email, String msg);
    void contactUsResponseSuccessFromModel(ContactUsReponseModel contactUsReponseModel);
    void contactUsResponseFailureFromModel(String msgg);
}
