package com.careS365.home.bottomFragments.presenter;

import com.careS365.home.bottomFragments.ISettingsFragment;
import com.careS365.home.bottomFragments.SettingsFragment;
import com.careS365.home.bottomFragments.model.IMSettingsFragment;
import com.careS365.home.bottomFragments.model.MSettingsFragment;
import com.careS365.responseModel.LogoutResponseModel;

public class PSettingsFragment implements IPSettingsFragment {

    ISettingsFragment iSettingsFragment;
    IMSettingsFragment imSettingsFragment;

    public PSettingsFragment(SettingsFragment settingsFragment) {
        this.iSettingsFragment = settingsFragment;
        this.imSettingsFragment = new MSettingsFragment(this);
    }

    @Override
    public void logout(String userId, String authToken) {
        imSettingsFragment.logout(userId,authToken);
    }

    @Override
    public void logoutSuccess(LogoutResponseModel getCirclesResponseModel) {
        iSettingsFragment.logoutSuccessFromPresenter(getCirclesResponseModel);
    }

    @Override
    public void logoutFailure(String message) {
        iSettingsFragment.logoutFailureFromPresenter(message);
    }
}
