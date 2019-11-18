package com.careS365.home.bottomFragments.model;

public class BeanUser {
    String username;
    String address;
    String time;
    String batteryPer;
    Double userLat;
    Double userLong;

    public BeanUser(Double userLat,Double userLong,String username, String address, String time, String batteryPer) {
        this.userLat = userLat;
        this.userLong = userLong;
        this.username = username;
        this.address = address;
        this.time = time;
        this.batteryPer = batteryPer;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBatteryPer() {
        return batteryPer;
    }

    public void setBatteryPer(String batteryPer) {
        this.batteryPer = batteryPer;
    }

    public Double getUserLat() {
        return userLat;
    }

    public void setUserLat(Double userLat) {
        this.userLat = userLat;
    }

    public Double getUserLong() {
        return userLong;
    }

    public void setUserLong(Double userLong) {
        this.userLong = userLong;
    }



}
