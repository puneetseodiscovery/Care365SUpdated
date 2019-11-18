package com.careS365.editCircle.presenter;

import com.careS365.responseModel.EditCircleNameResponseModel;

public interface IPEditCircleNameActivity {
    void saveCircleName(String circleName, String circleId);
    void editCircleNameResponseSuccessFromModel(EditCircleNameResponseModel editCircleNameResponseModel);
    void editCircleNameResponseFailureFromModel(String message);
}
