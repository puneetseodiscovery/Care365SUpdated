package com.careS365.home.bottomFragments.presenter;

import com.careS365.home.bottomFragments.IMembersFragment;
import com.careS365.home.bottomFragments.MembersFragment;
import com.careS365.home.bottomFragments.model.IMMembersFragment;
import com.careS365.home.bottomFragments.model.MMembersFragment;
import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;

public class PMembersFragment implements IPMembersFragment {

    IMembersFragment iMembersFragment;
    IMMembersFragment imMembersFragment;

    public PMembersFragment(MembersFragment membersFragment) {
        this.iMembersFragment = membersFragment;
        this.imMembersFragment = new MMembersFragment(this);
    }

    @Override
    public void getCircles(String userId) {
        imMembersFragment.getCircles(userId);
    }

    @Override
    public void getCirclesSuccess(GetCirclesResponseModel getCirclesResponseModel) {
        iMembersFragment.getCirclesSuccessFromPresenter(getCirclesResponseModel);
    }

    @Override
    public void getCirclesFailure(String message) {
        iMembersFragment.getCirclesFailureFromPresenter(message);
    }

    @Override
    public void getCirclesNoDataFailure(String msgg) {
        iMembersFragment.getCirclesNoDataFailureFromPresenter(msgg);
    }

    @Override
    public void getCircleMembers(String selectedCircle) {
        imMembersFragment.getCircleMembers(selectedCircle);
    }

    @Override
    public void getCircleMembersSuccess(GetCircleMembersResponseModel getCircleMembersResponseModel) {
        iMembersFragment.getCircleMembersSuccessFromPresenter(getCircleMembersResponseModel);
    }

    @Override
    public void getCircleMembersFailure(String msggg) {
        iMembersFragment.getCircleMembersFailureFromPresenter(msggg);
    }

    @Override
    public void notifyAllMebers(String user_id, String circle_id,String battery_percentage) {
        imMembersFragment.notifyAllMebers(user_id,circle_id,battery_percentage);
    }

    @Override
    public void sendNotificationAllSuccess(BatteryLowNotificationResponseModel batteryLowNotificationResponseModel) {
        iMembersFragment.sendNotificationAllSuccessFromPresenter(batteryLowNotificationResponseModel);
    }

    @Override
    public void sendNotificationAllFailed(String message) {
        iMembersFragment.sendNotificationAllFailedFromPresenter(message);
    }
}
