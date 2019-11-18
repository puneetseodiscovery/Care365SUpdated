package com.careS365.account.presenter;

import com.careS365.responseModel.EditUsernameResponseModel;

public interface IPEditUserNameActivity {
    void editUsername(String username,String userId);
    void editUsernameResponseSuccessFromModel(EditUsernameResponseModel editUsernameResponseModel);
    void editUsernameResponseFailureFromModel(String message);
}
