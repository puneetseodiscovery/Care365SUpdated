package com.careS365.account;

import com.careS365.responseModel.SaveProfilePicResponseModel;

public interface IAccountActivity {

    void saveProfilePicSuccessFromPresenterToActivity(SaveProfilePicResponseModel saveProfilePicResponseModel);
    void saveProfilePicFailureFromPresenterToActivity(String message);
}
