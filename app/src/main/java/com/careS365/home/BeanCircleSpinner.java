package com.careS365.home;

public class BeanCircleSpinner {

    public String value;
    public String id;

    public BeanCircleSpinner(String value, String id) {
        this.value = value;
        this.id = id;
    }

    @Override
    public String toString() {
        return value;
    }

}
