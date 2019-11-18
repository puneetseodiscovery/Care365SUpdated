package com.careS365.invitecode;

import com.careS365.responseModel.JoinCircleResponseModel;

public interface IPEnterInviteCodeActivity {
    void joinCircle(String inviteCode, String invitedBy, String invitedCircle, String userId);
    void joinCircleResponseSuccessFromModel(JoinCircleResponseModel joinCircleResponseModel);
    void joinCircleResponseFailureFromModel(String message);
    void joinCircleWrongDataResponseFailureFromModel(String msgg);
}
