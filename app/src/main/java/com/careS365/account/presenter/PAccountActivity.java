package com.careS365.account.presenter;

import com.careS365.account.AccountActivity;
import com.careS365.account.IAccountActivity;
import com.careS365.account.model.IMAccountActivity;
import com.careS365.account.model.MAccountActivity;
import com.careS365.responseModel.SaveProfilePicResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PAccountActivity implements IPAccountActivity {

    IAccountActivity iAccountActivity;
    IMAccountActivity imAccountActivity;

    public PAccountActivity(AccountActivity accountActivity) {
        iAccountActivity = accountActivity;
        imAccountActivity = new MAccountActivity(this);
    }

    @Override
    public void saveProfilePic(String userId,MultipartBody.Part image, RequestBody imgReq) {
        imAccountActivity.saveProfilePic(userId,image,imgReq);
    }

    @Override
    public void saveProfilePicResponseSuccessFromModel(SaveProfilePicResponseModel saveProfilePicResponseModel) {
        iAccountActivity.saveProfilePicSuccessFromPresenterToActivity(saveProfilePicResponseModel);
    }

    @Override
    public void saveProfilePicResponseFailureFromModel(String message) {
        iAccountActivity.saveProfilePicFailureFromPresenterToActivity(message);
    }
}
