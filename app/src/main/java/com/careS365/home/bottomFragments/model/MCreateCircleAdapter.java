package com.careS365.home.bottomFragments.model;

import android.os.Message;

import com.careS365.home.bottomFragments.presenter.IPCreateCircleAdapter;
import com.careS365.home.bottomFragments.presenter.PCreateCircleAdapter;
import com.careS365.responseModel.CreateCircleResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MCreateCircleAdapter implements IMCreateCircleAdapter {

    IPCreateCircleAdapter ipCreateCircleAdapter;

    public MCreateCircleAdapter(PCreateCircleAdapter pCreateCircleAdapter) {
        this.ipCreateCircleAdapter = pCreateCircleAdapter;
    }

    @Override
    public void createCircle(String cicleName, String userId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.createCircle(cicleName,userId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.CREATE_CIRCLE_SUCCESS:
                    CreateCircleResponseModel createCircleResponseModel = (CreateCircleResponseModel) msg.obj;
                    ipCreateCircleAdapter.createCircleSuccessResponse(createCircleResponseModel);
                    break;
                case APIInterface.CREATE_CIRCLE_FAILED:
                    String msgg = (String) msg.obj;
                    ipCreateCircleAdapter.createCircleFailureResponse(msgg);
                    break;
            }
        }
    };
}
