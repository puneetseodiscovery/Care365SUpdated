package com.careS365.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.notification.interfaces.INotificationActivity;
import com.careS365.notification.interfaces.IPNotificationActivity;
import com.careS365.notification.presenter.PNotificationActivity;
import com.careS365.notification.responseModel.NotificationAlertResponseModel;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends BaseClass implements INotificationActivity, View.OnClickListener {

    @BindView(R.id.switch_rangeAlert)
    Switch switch_rangeAlert;

    @BindView(R.id.switch_lowBattery)
    Switch switch_lowBattery;

    @BindView(R.id.iv_back)
    RelativeLayout iv_back;

    IPNotificationActivity ipNotificationActivity;

    //TODO: notification_type (1 for range_notification_status, 2 for battery_notification_status)
    //TODO: notification_status (1 for ON, 0 for Off)

    private String notification_type = "",notification_status="";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        Initialization();
        EventListner();
    }

    private void Initialization() {

        context=NotificationActivity.this;
        ipNotificationActivity = new PNotificationActivity(this);
        //TODO: checked true if status 1 and 0 for false
        if (Utility.getBatteryNotifyStatus().equals("1")){
            switch_lowBattery.setChecked(true);
        }else {
            switch_lowBattery.setChecked(false);
        }
        //TODO: checked true if status 1 and 0 for false
        if (Utility.getRangeNotifyStatus().equals("1")) {
            switch_rangeAlert.setChecked(true);
        }else {
            switch_rangeAlert.setChecked(false);
        }

    }

    private void EventListner() {

        iv_back.setOnClickListener(this);

        switch_lowBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //TODO: notification_type (2 for battery_notification_status)
                    notification_type = "2";
                    //TODO: notification_status (1 for ON, 0 for Off)
                    notification_status = "1";
                    //TODO: setNotificationAlert method call
                    setNotificationAlert(Utility.getUserId(),notification_status,notification_type);
                } else {
                    notification_status = "0";
                    notification_type = "2";
                    //TODO: setNotificationAlert method call
                    setNotificationAlert(Utility.getUserId(),notification_status,notification_type);

                }
            }
        });

        switch_rangeAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //TODO: notification_type (1 for range_notification_status)
                    //TODO: notification_status (1 for ON, 0 for Off)
                    notification_type = "1";
                    notification_status = "1";
                    //TODO: setNotificationAlert method call
                    setNotificationAlert(Utility.getUserId(),notification_status,notification_type);

                } else {
                    notification_status = "0";
                    notification_type = "1";
                    //TODO: setNotificationAlert method call
                    setNotificationAlert(Utility.getUserId(),notification_status,notification_type);
                }
            }
        });
    }
     //TODO: setNotificationAlert method for set notification ON and OFF
    public void setNotificationAlert(String user_id,String notification_status,String notification_type ) {
        if (Utility.isNetworkConnected(context)){
            //TODO: API method call
            ipNotificationActivity.setNotification(user_id,notification_status,notification_type);
        }else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onSuccessResponseFromPresenter(NotificationAlertResponseModel notificationAlertResponseModel) {


        if (notificationAlertResponseModel!=null){

        }

    }

    @Override
    public void onFailedResponseFromPresenter(String message) {

    }
}
