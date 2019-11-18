package com.careS365.editCircle.presenter;

import com.careS365.editCircle.EditCircleNameActivity;
import com.careS365.editCircle.IEditCircleNameActivity;
import com.careS365.editCircle.model.IMEditCircleNameActivity;
import com.careS365.editCircle.model.MEditCircleNameActivity;
import com.careS365.responseModel.EditCircleNameResponseModel;

public class PEditCircleNameActivity implements IPEditCircleNameActivity {

    IEditCircleNameActivity iEditCircleNameActivity;
    IMEditCircleNameActivity imEditCircleNameActivity;

    public PEditCircleNameActivity(EditCircleNameActivity editCircleNameActivity) {
        iEditCircleNameActivity = editCircleNameActivity;
        imEditCircleNameActivity = new MEditCircleNameActivity(this);
    }

    @Override
    public void saveCircleName(String circleName, String circleId) {
        imEditCircleNameActivity.saveCircleName(circleName,circleId);
    }

    @Override
    public void editCircleNameResponseSuccessFromModel(EditCircleNameResponseModel editCircleNameResponseModel) {
        iEditCircleNameActivity.editCircleNameSuccessFromPresenterToActivity(editCircleNameResponseModel);
    }

    @Override
    public void editCircleNameResponseFailureFromModel(String message) {
        iEditCircleNameActivity.editCircleNameFailureFromPresenterToActivity(message);
    }
}
