package com.careS365.deleteCircleMember;

import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;

public interface IDeleteCircleMemberActivity {
    void getCircleMembersSuccessFromPresenterToActivity(GetCircleMembersResponseModel getCircleMembersResponseModel);
    void getCircleMembersFailureFromPresenterToActivity(String msgg);
    void deleteCircleMembersSuccessFromPresenterToActivity(DeleteCircleMembersResponseModel deleteCircleMembersResponseModel);
    void deleteCircleMembersFailureFromPresenterToActivity(String message);
}
