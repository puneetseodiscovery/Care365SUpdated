package com.careS365.notification.interfaces;

import com.careS365.notification.responseModel.NotificationAlertResponseModel;

public interface INotificationActivity {

    void onSuccessResponseFromPresenter(NotificationAlertResponseModel notificationAlertResponseModel);
    void onFailedResponseFromPresenter(String message);
}
