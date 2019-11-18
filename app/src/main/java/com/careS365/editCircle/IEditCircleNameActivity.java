package com.careS365.editCircle;

import com.careS365.responseModel.EditCircleNameResponseModel;

public interface IEditCircleNameActivity {
    void editCircleNameSuccessFromPresenterToActivity(EditCircleNameResponseModel editCircleNameResponseModel);
    void editCircleNameFailureFromPresenterToActivity(String message);
}
