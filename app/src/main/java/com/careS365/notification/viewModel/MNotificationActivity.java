package com.careS365.notification.viewModel;

import android.os.Handler;
import android.os.Message;

import com.careS365.notification.interfaces.IMNotificationActivity;
import com.careS365.notification.interfaces.IPNotificationActivity;
import com.careS365.notification.presenter.PNotificationActivity;
import com.careS365.notification.responseModel.NotificationAlertResponseModel;
import com.careS365.retrofit.APIInterface;
import com.careS365.retrofit.RetrofitCalls;

public class MNotificationActivity implements IMNotificationActivity {

    IPNotificationActivity ipNotificationActivity;

    public MNotificationActivity(PNotificationActivity pNotificationActivity) {
        this.ipNotificationActivity = pNotificationActivity;
    }

    @Override
    public void notificationRestCall(String user_id, String notification_status, String notification_type) {
        RetrofitCalls retrofitCalls = new RetrofitCalls();
        retrofitCalls.set_notification_API(user_id, notification_status, notification_type, mHandler);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APIInterface.SETTING_NOTIFICATION_SUCCESS:
                    NotificationAlertResponseModel notificationAlertResponseModel=((NotificationAlertResponseModel)msg.obj);
                    ipNotificationActivity.onSuccessResponseFromModel(notificationAlertResponseModel);
                    break;
                case APIInterface.SETTING_NOTIFICATION_FAILEDE:
                    String msgFailed = String.valueOf(msg.obj);
                    ipNotificationActivity.onFailedResponseFromModel(msgFailed);
                    break;
            }
        }
    };
}
