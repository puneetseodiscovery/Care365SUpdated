package com.careS365.deleteCircleMember;

import android.os.Message;

import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MDeleteCircleMemberActivity implements IMDeleteCircleMemberActivity {

    IPDeleteCircleMemberActivity ipDeleteCircleMemberActivity;

    public MDeleteCircleMemberActivity(PDeleteCircleMemberActivity pDeleteCircleMemberActivity) {
        this.ipDeleteCircleMemberActivity = pDeleteCircleMemberActivity;
    }

    @Override
    public void getCircleMembers(String selectedCircle) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.getCircleMembers(selectedCircle,mHandler);
    }

    @Override
    public void deleteCircleMembers(String selectedCircle, String userId, String selectedUsers) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.deleteCircleMembers(selectedCircle,userId,selectedUsers,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.GET_CIRCLE_MEMBERS_SUCCESS:
                    GetCircleMembersResponseModel getCircleMembersResponseModel = (GetCircleMembersResponseModel) msg.obj;
                    ipDeleteCircleMemberActivity.getCircleMembersResponseSuccessFromModel(getCircleMembersResponseModel);
                    break;
                case APIInterface.GET_CIRCLE_MEMBERS_FAILED:
                    String msgg = (String) msg.obj;
                    ipDeleteCircleMemberActivity.getCircleMembersResponseFailureFromModel(msgg);
                    break;
                case APIInterface.DELETE_CIRCLE_MEMBERS_SUCCESS:
                    DeleteCircleMembersResponseModel deleteCircleMembersResponseModel = (DeleteCircleMembersResponseModel) msg.obj;
                    ipDeleteCircleMemberActivity.deleteCircleMembersResponseSuccessFromModel(deleteCircleMembersResponseModel);
                    break;
                case APIInterface.DELETE_CIRCLE_MEMBERS_FAILED:
                    String message = (String) msg.obj;
                    ipDeleteCircleMemberActivity.deleteCircleMembersResponseFailureFromModel(message);
                    break;
            }
        }
    };
}
