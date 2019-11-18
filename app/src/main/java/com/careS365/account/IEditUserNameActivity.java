package com.careS365.account;

import com.careS365.responseModel.EditUsernameResponseModel;

public interface IEditUserNameActivity {
    void editUsernameSuccessFromPresenterToActivity(EditUsernameResponseModel editUsernameResponseModel);
    void editUsernameFailureFromPresenterToActivity(String message);
}
