package com.careS365.deleteCircle;

import android.os.Message;

import com.careS365.responseModel.DeleteCircleResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MDeleteCircleActivity implements IMDeleteCircleActivity {

    IPDeleteCircleActivity ipDeleteCircleActivity;

    public MDeleteCircleActivity(PDeleteCircleActivity pDeleteCircleActivity) {
        this.ipDeleteCircleActivity = pDeleteCircleActivity;
    }

    @Override
    public void deleteCircle(String userId, String selectedCircle) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.deleteCircle(userId,selectedCircle,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.DELETE_CIRCLE_SUCCESS:
                    DeleteCircleResponseModel deleteCircleResponseModel = (DeleteCircleResponseModel) msg.obj;
                    ipDeleteCircleActivity.deleteCircleResponseSuccessFromModel(deleteCircleResponseModel);
                    break;
                case APIInterface.DELETE_CIRCLE_FAILED:
                    String message = (String) msg.obj;
                    ipDeleteCircleActivity.deleteCircleResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
