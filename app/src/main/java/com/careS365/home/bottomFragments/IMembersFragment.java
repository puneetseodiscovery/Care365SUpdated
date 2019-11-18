package com.careS365.home.bottomFragments;

import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;

public interface IMembersFragment {

    void getCirclesSuccessFromPresenter(GetCirclesResponseModel getCirclesResponseModel);
    void getCirclesFailureFromPresenter(String message);
    void getCirclesNoDataFailureFromPresenter(String msgg);
    void getCircleMembersSuccessFromPresenter(GetCircleMembersResponseModel getCircleMembersResponseModel);
    void getCircleMembersFailureFromPresenter(String msggg);

    void sendNotificationAllSuccessFromPresenter(BatteryLowNotificationResponseModel batteryLowNotificationResponseModel);
    void sendNotificationAllFailedFromPresenter(String message);
}
