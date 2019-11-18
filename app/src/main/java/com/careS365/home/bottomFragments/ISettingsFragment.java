package com.careS365.home.bottomFragments;

import com.careS365.responseModel.LogoutResponseModel;

public interface ISettingsFragment {
    void logoutSuccessFromPresenter(LogoutResponseModel getCirclesResponseModel);
    void logoutFailureFromPresenter(String message);
}
