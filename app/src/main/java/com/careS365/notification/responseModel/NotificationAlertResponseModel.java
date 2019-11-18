package com.careS365.notification.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationAlertResponseModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("range_notify_status")
        @Expose
        private String rangeNotifyStatus;
        @SerializedName("battery_notify_status")
        @Expose
        private String batteryNotifyStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getRangeNotifyStatus() {
            return rangeNotifyStatus;
        }

        public void setRangeNotifyStatus(String rangeNotifyStatus) {
            this.rangeNotifyStatus = rangeNotifyStatus;
        }

        public String getBatteryNotifyStatus() {
            return batteryNotifyStatus;
        }

        public void setBatteryNotifyStatus(String batteryNotifyStatus) {
            this.batteryNotifyStatus = batteryNotifyStatus;
        }

    }
}
