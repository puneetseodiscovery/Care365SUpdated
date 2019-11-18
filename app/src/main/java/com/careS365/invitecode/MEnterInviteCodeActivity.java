package com.careS365.invitecode;

import android.os.Message;

import com.careS365.responseModel.JoinCircleResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MEnterInviteCodeActivity implements IMEnterInviteCodeActivity {

    IPEnterInviteCodeActivity ipEnterInviteCodeActivity;

    public MEnterInviteCodeActivity(PEnterInviteCodeActivity pEnterInviteCodeActivity) {
        this.ipEnterInviteCodeActivity = pEnterInviteCodeActivity;
    }

    @Override
    public void joinCircle(String inviteCode, String invitedBy, String invitedCircle, String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.joinCircle(inviteCode,invitedBy,invitedCircle,userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.JOIN_CIRCLE_SUCCESS:
                    JoinCircleResponseModel joinCircleResponseModel = (JoinCircleResponseModel) msg.obj;
                    ipEnterInviteCodeActivity.joinCircleResponseSuccessFromModel(joinCircleResponseModel);
                    break;
                case APIInterface.JOIN_CIRCLE_FAILED_WRONG_DATA:
                    String msgg = (String) msg.obj;
                    ipEnterInviteCodeActivity.joinCircleWrongDataResponseFailureFromModel(msgg);
                    break;
                case APIInterface.JOIN_CIRCLE_FAILED:
                    String message = (String) msg.obj;
                    ipEnterInviteCodeActivity.joinCircleResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
