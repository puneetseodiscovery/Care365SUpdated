package com.careS365.invitecode;

import com.careS365.responseModel.JoinCircleResponseModel;

public interface IEnterInviteCodeActivity {
    void joinCircleSuccessFromPresenterToActivity(JoinCircleResponseModel joinCircleResponseModel);
    void joinCircleFailureFromPresenterToActivity(String message);
    void joinCircleWrongDataFailureFromPresenterToActivity(String msgg);
}
