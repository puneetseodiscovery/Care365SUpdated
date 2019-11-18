package com.careS365.notification.interfaces;

import com.careS365.notification.responseModel.NotificationAlertResponseModel;

public interface IPNotificationActivity {

    void setNotification(String user_id,String notification_status,String notification_type);
    void onSuccessResponseFromModel(NotificationAlertResponseModel notificationAlertResponseModel);
    void onFailedResponseFromModel(String message);

}
