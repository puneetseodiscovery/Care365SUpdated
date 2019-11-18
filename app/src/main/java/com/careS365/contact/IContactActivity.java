package com.careS365.contact;

import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;

public interface IContactActivity {
    void getAdminEmailSuccessFromPresenterToActivity(GetAdminEmailResponseModel getAdminEmailResponseModel);
    void getAdminEmailFailureFromPresenterToActivity(String message);
    void contactUsSuccessFromPresenterToActivity(ContactUsReponseModel contactUsReponseModel);
    void contactUsFailureFromPresenterToActivity(String msgg);
}
