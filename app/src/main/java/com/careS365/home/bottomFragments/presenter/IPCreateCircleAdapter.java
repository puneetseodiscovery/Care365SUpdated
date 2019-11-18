package com.careS365.home.bottomFragments.presenter;

import com.careS365.responseModel.CreateCircleResponseModel;

public interface IPCreateCircleAdapter {
    void createCircle(String cicleName, String userId);
    void createCircleSuccessResponse(CreateCircleResponseModel createCircleResponseModel);
    void createCircleFailureResponse(String msgg);
}
