package com.careS365.account;

import com.careS365.responseModel.DeleteAccountResponseModel;

public interface IDeleteAccountActivity {
    void deleteAccountSuccessFromPresenterToActivity(DeleteAccountResponseModel deleteAccountResponseModel);
    void deleteAccountFailureFromPresenterToActivity(String message);
}
