package com.careS365.home.bottomFragments.model;

import android.os.Message;

import com.careS365.home.bottomFragments.presenter.IPSettingsFragment;
import com.careS365.home.bottomFragments.presenter.PSettingsFragment;
import com.careS365.responseModel.LogoutResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MSettingsFragment implements IMSettingsFragment {

    IPSettingsFragment ipSettingsFragment;

    public MSettingsFragment(PSettingsFragment pSettingsFragment) {
        this.ipSettingsFragment = pSettingsFragment;
    }

    @Override
    public void logout(String userId, String authToken) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.logout(userId,authToken,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.LOGOUT_SUCCESS:
                    LogoutResponseModel getCirclesResponseModel = (LogoutResponseModel) msg.obj;
                    ipSettingsFragment.logoutSuccess(getCirclesResponseModel);
                    break;

                case APIInterface.LOGOUT_FAILED:
                    String message = (String) msg.obj;
                    ipSettingsFragment.logoutFailure(message);
                    break;
            }
        }
    };
}
