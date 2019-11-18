package com.careS365.notification.presenter;

import com.careS365.notification.NotificationActivity;
import com.careS365.notification.interfaces.IMNotificationActivity;
import com.careS365.notification.interfaces.INotificationActivity;
import com.careS365.notification.interfaces.IPNotificationActivity;
import com.careS365.notification.responseModel.NotificationAlertResponseModel;
import com.careS365.notification.viewModel.MNotificationActivity;

public class PNotificationActivity implements IPNotificationActivity {

    INotificationActivity iNotificationActivity;
    IMNotificationActivity imNotificationActivity;

    public PNotificationActivity(NotificationActivity notificationActivity) {
        this.iNotificationActivity=notificationActivity;
    }

    @Override
    public void setNotification(String user_id, String notification_status, String notification_type) {
        imNotificationActivity=new MNotificationActivity(this);
        imNotificationActivity.notificationRestCall(user_id,notification_status,notification_type);
    }

    @Override
    public void onSuccessResponseFromModel(NotificationAlertResponseModel notificationAlertResponseModel) {
        iNotificationActivity.onSuccessResponseFromPresenter(notificationAlertResponseModel);
    }

    @Override
    public void onFailedResponseFromModel(String message) {
        iNotificationActivity.onFailedResponseFromPresenter(message);
    }
}
