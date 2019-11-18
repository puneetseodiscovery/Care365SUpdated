package com.careS365.account.model;

import android.os.Message;

import com.careS365.account.presenter.IPAccountActivity;
import com.careS365.account.presenter.PAccountActivity;
import com.careS365.responseModel.SaveProfilePicResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MAccountActivity implements IMAccountActivity {

    IPAccountActivity ipAccountActivity;

    public MAccountActivity(PAccountActivity pAccountActivity) {
        this.ipAccountActivity = pAccountActivity;
    }

    @Override
    public void saveProfilePic(String userId,MultipartBody.Part image, RequestBody imgReq) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.saveProfilePic(userId,image,imgReq,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.SAVE_PROFILE_PIC_SUCCESS:
                    SaveProfilePicResponseModel saveProfilePicResponseModel = (SaveProfilePicResponseModel) msg.obj;
                    ipAccountActivity.saveProfilePicResponseSuccessFromModel(saveProfilePicResponseModel);
                    break;
                case APIInterface.SAVE_PROFILE_PIC_FAILED:
                    String message = (String) msg.obj;
                    ipAccountActivity.saveProfilePicResponseFailureFromModel(message);
                    break;
            }
        }
    };
}
