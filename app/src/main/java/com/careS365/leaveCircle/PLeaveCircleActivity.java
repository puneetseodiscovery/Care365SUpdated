package com.careS365.leaveCircle;

import com.careS365.responseModel.LeaveCircleResponseModel;

public class PLeaveCircleActivity implements IPLeaveCircleActivity {

    ILeaveCircleActivity iLeaveCircleActivity;
    IMLeaveCircleActivity imLeaveCircleActivity;

    public PLeaveCircleActivity(LeaveCircleActivity leaveCircleActivity) {
        iLeaveCircleActivity = leaveCircleActivity;
        imLeaveCircleActivity = new MLeaveCircleActivity(this);
    }

    @Override
    public void leaveCircle(String userId, String selectedCircle) {
        imLeaveCircleActivity.leaveCircle(userId,selectedCircle);
    }

    @Override
    public void leaveCircleResponseSuccessFromModel(LeaveCircleResponseModel leaveCircleResponseModel) {
        iLeaveCircleActivity.leaveCircleSuccessFromPresenterToActivity(leaveCircleResponseModel);
    }

    @Override
    public void leaveCircleResponseFailureFromModel(String message) {
        iLeaveCircleActivity.leaveCircleFailureFromPresenterToActivity(message);
    }

    @Override
    public void leaveCircleOwnerResponseFailureFromModel(String msgg) {
        iLeaveCircleActivity.leaveCircleOwnerFailureFromPresenterToActivity(msgg);
    }
}
