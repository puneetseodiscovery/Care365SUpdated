package com.careS365.leaveCircle;

import com.careS365.responseModel.LeaveCircleResponseModel;

public interface ILeaveCircleActivity {
    void leaveCircleSuccessFromPresenterToActivity(LeaveCircleResponseModel leaveCircleResponseModel);
    void leaveCircleFailureFromPresenterToActivity(String message);
    void leaveCircleOwnerFailureFromPresenterToActivity(String msgg);
}
