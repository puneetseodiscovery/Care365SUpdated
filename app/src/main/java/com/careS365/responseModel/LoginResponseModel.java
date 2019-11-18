package com.careS365.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("verify_otp")
    @Expose
    private String verifyOtp;
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

    public String getVerifyOtp() {
        return verifyOtp;
    }

    public void setVerifyOtp(String verifyOtp) {
        this.verifyOtp = verifyOtp;
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
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone_num")
        @Expose
        private String phoneNum;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("address_latitude")
        @Expose
        private String addressLatitude;
        @SerializedName("address_logitude")
        @Expose
        private String addressLogitude;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("auth_token")
        @Expose
        private String authToken;
        @SerializedName("device_token")
        @Expose
        private String deviceToken;
        @SerializedName("invite code")
        @Expose
        private String inviteCode;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("pass_otp")
        @Expose
        private String passOtp;
        @SerializedName("verify_otp")
        @Expose
        private String verifyOtp;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("range_notify_status")
        @Expose
        private String rangeNotifyStatus;
        @SerializedName("battery_notify_status")
        @Expose
        private String batteryNotifyStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressLatitude() {
            return addressLatitude;
        }

        public void setAddressLatitude(String addressLatitude) {
            this.addressLatitude = addressLatitude;
        }

        public String getAddressLogitude() {
            return addressLogitude;
        }

        public void setAddressLogitude(String addressLogitude) {
            this.addressLogitude = addressLogitude;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getPassOtp() {
            return passOtp;
        }

        public void setPassOtp(String passOtp) {
            this.passOtp = passOtp;
        }

        public String getVerifyOtp() {
            return verifyOtp;
        }

        public void setVerifyOtp(String verifyOtp) {
            this.verifyOtp = verifyOtp;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

}
