package com.careS365.deleteCircleMember;

import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;

public interface IPDeleteCircleMemberActivity {
    void getCircleMembersResponseSuccessFromModel(GetCircleMembersResponseModel getCircleMembersResponseModel);
    void getCircleMembersResponseFailureFromModel(String msgg);
    void getCircleMembers(String selectedCircle);
    void deleteCircleMembers(String selectedCircle, String userId, String selectedUsers);
    void deleteCircleMembersResponseSuccessFromModel(DeleteCircleMembersResponseModel deleteCircleMembersResponseModel);
    void deleteCircleMembersResponseFailureFromModel(String message);
}
