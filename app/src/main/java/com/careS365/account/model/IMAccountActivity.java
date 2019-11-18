package com.careS365.account.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IMAccountActivity {
    void saveProfilePic(String userId,MultipartBody.Part image, RequestBody imgReq);
}
