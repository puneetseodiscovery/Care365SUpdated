package com.careS365.deleteCircleMember;

import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;

public class PDeleteCircleMemberActivity implements IPDeleteCircleMemberActivity {

    IDeleteCircleMemberActivity iDeleteCircleMemberActivity;
    IMDeleteCircleMemberActivity imDeleteCircleMemberActivity;

    public PDeleteCircleMemberActivity(DeleteCircleMemberActivity deleteCircleMemberActivity) {
        iDeleteCircleMemberActivity = deleteCircleMemberActivity;
        imDeleteCircleMemberActivity = new MDeleteCircleMemberActivity(this);
    }

    @Override
    public void getCircleMembersResponseSuccessFromModel(GetCircleMembersResponseModel getCircleMembersResponseModel) {
        iDeleteCircleMemberActivity.getCircleMembersSuccessFromPresenterToActivity(getCircleMembersResponseModel);
    }

    @Override
    public void getCircleMembersResponseFailureFromModel(String msgg) {
        iDeleteCircleMemberActivity.getCircleMembersFailureFromPresenterToActivity(msgg);
    }

    @Override
    public void getCircleMembers(String selectedCircle) {
        imDeleteCircleMemberActivity.getCircleMembers(selectedCircle);
    }

    @Override
    public void deleteCircleMembers(String selectedCircle, String userId, String selectedUsers) {
        imDeleteCircleMemberActivity.deleteCircleMembers(selectedCircle,userId,selectedUsers);
    }

    @Override
    public void deleteCircleMembersResponseSuccessFromModel(DeleteCircleMembersResponseModel deleteCircleMembersResponseModel) {
        iDeleteCircleMemberActivity.deleteCircleMembersSuccessFromPresenterToActivity(deleteCircleMembersResponseModel);
    }

    @Override
    public void deleteCircleMembersResponseFailureFromModel(String message) {
        iDeleteCircleMemberActivity.deleteCircleMembersFailureFromPresenterToActivity(message);
    }
}
