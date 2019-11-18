package com.careS365.home.bottomFragments.model;

import android.os.Message;

import com.careS365.home.bottomFragments.presenter.IPMembersFragment;
import com.careS365.home.bottomFragments.presenter.PMembersFragment;
import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MMembersFragment implements IMMembersFragment {

    IPMembersFragment ipMembersFragment;

    public MMembersFragment(PMembersFragment pMembersFragment) {
        this.ipMembersFragment = pMembersFragment;
    }

    @Override
    public void getCircles(String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.getCircles(userId,mHandler);
    }

    @Override
    public void getCircleMembers(String selectedCircle) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.getCircleMembers(selectedCircle,mHandler);
    }

    @Override
    public void notifyAllMebers(String user_id, String circle_id,String battery_percentage) {
        RetrofitCalls retrofitCalls=new RetrofitCalls();
        retrofitCalls.notifyAllMembers(user_id,circle_id,battery_percentage,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.GET_CIRCLES_SUCCESS:
                    GetCirclesResponseModel getCirclesResponseModel = (GetCirclesResponseModel) msg.obj;
                    ipMembersFragment.getCirclesSuccess(getCirclesResponseModel);
                    break;

                case APIInterface.GET_CIRCLES_FAILED:
                    String message = (String) msg.obj;
                    ipMembersFragment.getCirclesFailure(message);
                    break;

                case APIInterface.GET_CIRCLES_NO_DATA:
                    String msgg = (String) msg.obj;
                    ipMembersFragment.getCirclesNoDataFailure(msgg);
                    break;

                case APIInterface.GET_CIRCLE_MEMBERS_SUCCESS:
                    GetCircleMembersResponseModel getCircleMembersResponseModel = (GetCircleMembersResponseModel) msg.obj;
                    ipMembersFragment.getCircleMembersSuccess(getCircleMembersResponseModel);
                    break;

                case APIInterface.GET_CIRCLE_MEMBERS_FAILED:
                    String msggg = (String) msg.obj;
                    ipMembersFragment.getCircleMembersFailure(msggg);
                    break;

                case APIInterface.NOTIFIY__ALL_MEMBERS_SUCCESS:
                    BatteryLowNotificationResponseModel responseModelbattery = (BatteryLowNotificationResponseModel) msg.obj;
                    ipMembersFragment.sendNotificationAllSuccess(responseModelbattery);
                    break;

                case APIInterface.NOTIFIY__ALL_MEMBERS_FAILD:
                    String msgfailed = (String) msg.obj;
                    ipMembersFragment.sendNotificationAllFailed(msgfailed);
                    break;
            }
        }
    };
}
