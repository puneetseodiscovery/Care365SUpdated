package com.careS365.home.bottomFragments.adapter;

import com.careS365.responseModel.CreateCircleResponseModel;

public interface ICreateCircleAdapter {
    void createCircleSuccessFromPresenter(CreateCircleResponseModel createCircleResponseModel);
    void createCircleFailureFromPresenter(String msgg);
}
