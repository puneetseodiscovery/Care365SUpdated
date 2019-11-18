package com.careS365.account.presenter;

import com.careS365.account.DeleteAccountActivity;
import com.careS365.account.IDeleteAccountActivity;
import com.careS365.account.model.IMDeleteAccountActivity;
import com.careS365.account.model.MDeleteAccountActivity;
import com.careS365.responseModel.DeleteAccountResponseModel;

public class PDeleteAccountActivity implements IPDeleteAccountActivity {

    IDeleteAccountActivity iDeleteAccountActivity;
    IMDeleteAccountActivity imDeleteAccountActivity;

    public PDeleteAccountActivity(DeleteAccountActivity deleteAccountActivity) {
        iDeleteAccountActivity = deleteAccountActivity;
        imDeleteAccountActivity = new MDeleteAccountActivity(this);
    }

    @Override
    public void deleteAccount(String userId) {
        imDeleteAccountActivity.deleteAccount(userId);
    }

    @Override
    public void deleteAccountResponseSuccessFromModel(DeleteAccountResponseModel deleteAccountResponseModel) {
        iDeleteAccountActivity.deleteAccountSuccessFromPresenterToActivity(deleteAccountResponseModel);
    }

    @Override
    public void deleteAccountResponseFailureFromModel(String message) {
        iDeleteAccountActivity.deleteAccountFailureFromPresenterToActivity(message);
    }
}
