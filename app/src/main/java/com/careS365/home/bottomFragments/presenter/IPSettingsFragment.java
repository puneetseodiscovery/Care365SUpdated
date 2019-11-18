package com.careS365.home.bottomFragments.presenter;

import com.careS365.responseModel.LogoutResponseModel;

public interface IPSettingsFragment {
    void logout(String userId, String authToken);
    void logoutSuccess(LogoutResponseModel getCirclesResponseModel);
    void logoutFailure(String message);
}
