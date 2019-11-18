package com.careS365.leaveCircle;

import com.careS365.responseModel.LeaveCircleResponseModel;

public interface IPLeaveCircleActivity {
    void leaveCircle(String userId, String selectedCircle);
    void leaveCircleResponseSuccessFromModel(LeaveCircleResponseModel leaveCircleResponseModel);
    void leaveCircleResponseFailureFromModel(String message);
    void leaveCircleOwnerResponseFailureFromModel(String msgg);
}
