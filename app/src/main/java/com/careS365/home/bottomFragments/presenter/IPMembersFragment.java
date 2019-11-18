package com.careS365.home.bottomFragments.presenter;

import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;

public interface IPMembersFragment {

    void getCircles(String userId);
    void getCirclesSuccess(GetCirclesResponseModel getCirclesResponseModel);
    void getCirclesFailure(String message);

    void getCirclesNoDataFailure(String msgg);
    void getCircleMembers(String selectedCircle);
    void getCircleMembersSuccess(GetCircleMembersResponseModel getCircleMembersResponseModel);
    void getCircleMembersFailure(String msggg);

    void notifyAllMebers(String user_id, String circle_id,String battery_percentage);
    void sendNotificationAllSuccess(BatteryLowNotificationResponseModel batteryLowNotificationResponseModel);
    void sendNotificationAllFailed(String message);

}
