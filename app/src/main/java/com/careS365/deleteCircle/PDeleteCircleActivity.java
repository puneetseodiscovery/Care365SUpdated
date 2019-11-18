package com.careS365.deleteCircle;

import com.careS365.responseModel.DeleteCircleResponseModel;

public class PDeleteCircleActivity implements IPDeleteCircleActivity {

    IDeleteCircleActivity iDeleteCircleActivity;
    IMDeleteCircleActivity imDeleteCircleActivity;

    public PDeleteCircleActivity(DeleteCircleActivity deleteCircleActivity) {
        iDeleteCircleActivity = deleteCircleActivity;
        imDeleteCircleActivity = new MDeleteCircleActivity(this);
    }

    @Override
    public void deleteCircle(String userId, String selectedCircle) {
        imDeleteCircleActivity.deleteCircle(userId,selectedCircle);
    }

    @Override
    public void deleteCircleResponseSuccessFromModel(DeleteCircleResponseModel deleteCircleResponseModel) {
        iDeleteCircleActivity.deleteCircleSuccessFromPresenterToActivity(deleteCircleResponseModel);
    }

    @Override
    public void deleteCircleResponseFailureFromModel(String message) {
        iDeleteCircleActivity.deleteCircleFailureFromPresenterToActivity(message);
    }
}
