package com.careS365.leaveCircle;

import android.os.Message;

import com.careS365.responseModel.LeaveCircleResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MLeaveCircleActivity implements IMLeaveCircleActivity {

    IPLeaveCircleActivity ipLeaveCircleActivity;

    public MLeaveCircleActivity(PLeaveCircleActivity pLeaveCircleActivity) {
        this.ipLeaveCircleActivity = pLeaveCircleActivity;
    }

    @Override
    public void leaveCircle(String userId, String selectedCircle) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.leaveCircle(userId,selectedCircle,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.LEAVE_CIRCLE_SUCCESS:
                    LeaveCircleResponseModel leaveCircleResponseModel = (LeaveCircleResponseModel) msg.obj;
                    ipLeaveCircleActivity.leaveCircleResponseSuccessFromModel(leaveCircleResponseModel);
                    break;
                case APIInterface.LEAVE_CIRCLE_OWNER_FAILED:
                    String msgg = (String) msg.obj;
                    ipLeaveCircleActivity.leaveCircleOwnerResponseFailureFromModel(msgg);
                    break;
                case APIInterface.LEAVE_CIRCLE_FAILED:
                    String message = (String) msg.obj;
                    ipLeaveCircleActivity.leaveCircleResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
