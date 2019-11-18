package com.careS365.editCircle.model;

import android.os.Message;

import com.careS365.editCircle.presenter.IPEditCircleNameActivity;
import com.careS365.editCircle.presenter.PEditCircleNameActivity;
import com.careS365.responseModel.EditCircleNameResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MEditCircleNameActivity implements IMEditCircleNameActivity {

    IPEditCircleNameActivity ipEditCircleNameActivity;

    public MEditCircleNameActivity(PEditCircleNameActivity pEditCircleNameActivity) {
        this.ipEditCircleNameActivity = pEditCircleNameActivity;
    }

    @Override
    public void saveCircleName(String circleName, String circleId) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.saveCircleName(circleName,circleId,mHandler);
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case APIInterface.EDIT_CIRCLE_NAME_SUCCESS:
                    EditCircleNameResponseModel editCircleNameResponseModel = (EditCircleNameResponseModel) msg.obj;
                    ipEditCircleNameActivity.editCircleNameResponseSuccessFromModel(editCircleNameResponseModel);
                    break;
                case APIInterface.EDIT_CIRCLE_NAME_FAILED:
                    String message = (String) msg.obj;
                    ipEditCircleNameActivity.editCircleNameResponseFailureFromModel(message);
                    break;

            }
        }
    };
}
