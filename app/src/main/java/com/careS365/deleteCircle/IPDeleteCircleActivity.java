package com.careS365.deleteCircle;

import com.careS365.responseModel.DeleteCircleResponseModel;

public interface IPDeleteCircleActivity {
    void deleteCircle(String userId, String selectedCircle);
    void deleteCircleResponseSuccessFromModel(DeleteCircleResponseModel deleteCircleResponseModel);
    void deleteCircleResponseFailureFromModel(String message);
}
