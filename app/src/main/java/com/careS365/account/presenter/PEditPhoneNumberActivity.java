package com.careS365.account.presenter;

import com.careS365.account.EditPhoneNumberActivity;
import com.careS365.account.IEditPhoneNumberActivity;
import com.careS365.account.model.IMEditPhoneNumberActivity;
import com.careS365.account.model.MEditPhoneNumberActivity;
import com.careS365.responseModel.EditPhoneResponseModel;

public class PEditPhoneNumberActivity implements IPEditPhoneNumberActivity {

    IEditPhoneNumberActivity iEditPhoneNumberActivity;
    IMEditPhoneNumberActivity imEditPhoneNumberActivity;

    public PEditPhoneNumberActivity(EditPhoneNumberActivity editPhoneNumberActivity) {
        iEditPhoneNumberActivity = editPhoneNumberActivity;
        imEditPhoneNumberActivity = new MEditPhoneNumberActivity(this);
    }

    @Override
    public void editPhone(String phone, String userId) {
        imEditPhoneNumberActivity.editPhone(phone,userId);
    }

    @Override
    public void editPhoneResponseSuccessFromModel(EditPhoneResponseModel editPhoneResponseModel) {
        iEditPhoneNumberActivity.editPhoneSuccessFromPresenterToActivity(editPhoneResponseModel);
    }

    @Override
    public void editPhoneResponseFailureFromModel(String message) {
        iEditPhoneNumberActivity.editPhoneFailureFromPresenterToActivity(message);
    }
}
