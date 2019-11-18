package com.careS365.account.presenter;

import com.careS365.account.EditUserNameActivity;
import com.careS365.account.IEditUserNameActivity;
import com.careS365.account.model.IMEditUserNameActivity;
import com.careS365.account.model.MEditUserNameActivity;
import com.careS365.responseModel.EditUsernameResponseModel;

public class PEditUserNameActivity implements IPEditUserNameActivity {

    IEditUserNameActivity iEditUserNameActivity;
    IMEditUserNameActivity imEditUserNameActivity;

    public PEditUserNameActivity(EditUserNameActivity editUserNameActivity) {
        iEditUserNameActivity = editUserNameActivity;
        imEditUserNameActivity = new MEditUserNameActivity(this);
    }

    @Override
    public void editUsername(String username, String userId) {
        imEditUserNameActivity.editUsername(username,userId);
    }

    @Override
    public void editUsernameResponseSuccessFromModel(EditUsernameResponseModel editUsernameResponseModel) {
        iEditUserNameActivity.editUsernameSuccessFromPresenterToActivity(editUsernameResponseModel);
    }

    @Override
    public void editUsernameResponseFailureFromModel(String message) {
        iEditUserNameActivity.editUsernameFailureFromPresenterToActivity(message);
    }
}
