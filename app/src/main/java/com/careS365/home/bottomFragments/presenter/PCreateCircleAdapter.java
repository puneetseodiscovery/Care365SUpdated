package com.careS365.home.bottomFragments.presenter;

import com.careS365.home.bottomFragments.adapter.CreateCircleAdapter;
import com.careS365.home.bottomFragments.adapter.ICreateCircleAdapter;
import com.careS365.home.bottomFragments.model.IMCreateCircleAdapter;
import com.careS365.home.bottomFragments.model.MCreateCircleAdapter;
import com.careS365.responseModel.CreateCircleResponseModel;

public class PCreateCircleAdapter implements IPCreateCircleAdapter {

    ICreateCircleAdapter iCreateCircleAdapter;
    IMCreateCircleAdapter imCreateCircleAdapter;

    public PCreateCircleAdapter(CreateCircleAdapter createCircleAdapter) {
        iCreateCircleAdapter = createCircleAdapter;
        imCreateCircleAdapter = new MCreateCircleAdapter(this);
    }

    @Override
    public void createCircle(String cicleName, String userId) {
        imCreateCircleAdapter.createCircle(cicleName,userId);
    }

    @Override
    public void createCircleSuccessResponse(CreateCircleResponseModel createCircleResponseModel) {
        iCreateCircleAdapter.createCircleSuccessFromPresenter(createCircleResponseModel);
    }

    @Override
    public void createCircleFailureResponse(String msgg) {
        iCreateCircleAdapter.createCircleFailureFromPresenter(msgg);
    }
}
