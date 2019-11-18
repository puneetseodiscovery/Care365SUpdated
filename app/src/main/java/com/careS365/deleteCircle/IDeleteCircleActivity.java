package com.careS365.deleteCircle;

import com.careS365.responseModel.DeleteCircleResponseModel;

public interface IDeleteCircleActivity {
    void deleteCircleSuccessFromPresenterToActivity(DeleteCircleResponseModel deleteCircleResponseModel);
    void deleteCircleFailureFromPresenterToActivity(String message);
}
