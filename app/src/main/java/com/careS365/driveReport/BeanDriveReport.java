package com.careS365.driveReport;

public class BeanDriveReport {
    String day;
    String locationStay;
    String locationTime;
    String driveDistance;
    String driveTime;


    public BeanDriveReport(String day, String locationStay, String locationTime, String driveDistance, String driveTime) {
        this.day = day;
        this.locationStay = locationStay;
        this.locationTime = locationTime;
        this.driveDistance = driveDistance;
        this.driveTime = driveTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocationStay() {
        return locationStay;
    }

    public void setLocationStay(String locationStay) {
        this.locationStay = locationStay;
    }

    public String getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(String locationTime) {
        this.locationTime = locationTime;
    }

    public String getDriveDistance() {
        return driveDistance;
    }

    public void setDriveDistance(String driveDistance) {
        this.driveDistance = driveDistance;
    }

    public String getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(String driveTime) {
        this.driveTime = driveTime;
    }
}
