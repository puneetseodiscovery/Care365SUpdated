package com.careS365.account;

import com.careS365.responseModel.EditPhoneResponseModel;

public interface IEditPhoneNumberActivity {
    void editPhoneSuccessFromPresenterToActivity(EditPhoneResponseModel editPhoneResponseModel);
    void editPhoneFailureFromPresenterToActivity(String message);
}
