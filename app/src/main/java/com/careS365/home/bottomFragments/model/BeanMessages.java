package com.careS365.home.bottomFragments.model;

public class BeanMessages {
    int personImg;
    String message;
    String personName;
    String time;

    public BeanMessages(int personImg, String message, String personName, String time) {
        this.personImg = personImg;
        this.message = message;
        this.personName = personName;
        this.time = time;
    }

    public int getPersonImg() {
        return personImg;
    }

    public void setPersonImg(int personImg) {
        this.personImg = personImg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
