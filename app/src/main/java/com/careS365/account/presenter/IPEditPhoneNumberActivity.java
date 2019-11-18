package com.careS365.account.presenter;

import com.careS365.responseModel.EditPhoneResponseModel;

public interface IPEditPhoneNumberActivity {
    void editPhone(String phone, String userId);
    void editPhoneResponseSuccessFromModel(EditPhoneResponseModel editPhoneResponseModel);
    void editPhoneResponseFailureFromModel(String message);
}
