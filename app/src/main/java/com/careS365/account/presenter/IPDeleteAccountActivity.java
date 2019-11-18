package com.careS365.account.presenter;

import com.careS365.responseModel.DeleteAccountResponseModel;

public interface IPDeleteAccountActivity {
    void deleteAccount(String userId);
    void deleteAccountResponseSuccessFromModel(DeleteAccountResponseModel deleteAccountResponseModel);
    void deleteAccountResponseFailureFromModel(String message);
}
