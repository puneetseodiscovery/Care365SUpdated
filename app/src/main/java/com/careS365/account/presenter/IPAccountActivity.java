package com.careS365.account.presenter;

import com.careS365.responseModel.SaveProfilePicResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IPAccountActivity {
    void saveProfilePic(String userId,MultipartBody.Part image, RequestBody imgReq);
    void saveProfilePicResponseSuccessFromModel(SaveProfilePicResponseModel saveProfilePicResponseModel);
    void saveProfilePicResponseFailureFromModel(String message);
}
