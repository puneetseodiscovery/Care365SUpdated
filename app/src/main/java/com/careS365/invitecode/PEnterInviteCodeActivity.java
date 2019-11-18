package com.careS365.invitecode;

import com.careS365.responseModel.JoinCircleResponseModel;

public class PEnterInviteCodeActivity implements IPEnterInviteCodeActivity {

    IEnterInviteCodeActivity iEnterInviteCodeActivity;
    IMEnterInviteCodeActivity imEnterInviteCodeActivity;

    public PEnterInviteCodeActivity(EnterInviteCodeActivity enterInviteCodeActivity) {
        iEnterInviteCodeActivity = enterInviteCodeActivity;
        imEnterInviteCodeActivity = new MEnterInviteCodeActivity(this);
    }

    @Override
    public void joinCircle(String inviteCode, String invitedBy, String invitedCircle, String userId) {
        imEnterInviteCodeActivity.joinCircle(inviteCode,invitedBy,invitedCircle,userId);
    }

    @Override
    public void joinCircleResponseSuccessFromModel(JoinCircleResponseModel joinCircleResponseModel) {
        iEnterInviteCodeActivity.joinCircleSuccessFromPresenterToActivity(joinCircleResponseModel);
    }

    @Override
    public void joinCircleResponseFailureFromModel(String message) {
        iEnterInviteCodeActivity.joinCircleFailureFromPresenterToActivity(message);
    }

    @Override
    public void joinCircleWrongDataResponseFailureFromModel(String msgg) {
        iEnterInviteCodeActivity.joinCircleWrongDataFailureFromPresenterToActivity(msgg);
    }
}
