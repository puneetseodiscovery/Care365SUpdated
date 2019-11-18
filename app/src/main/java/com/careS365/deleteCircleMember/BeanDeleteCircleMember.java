package com.careS365.deleteCircleMember;

public class BeanDeleteCircleMember {
    String personId;
    String personImg;
    String personName;

    public BeanDeleteCircleMember(String personId, String personImg, String personName) {
        this.personId = personId;
        this.personImg = personImg;
        this.personName = personName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonImg() {
        return personImg;
    }

    public void setPersonImg(String personImg) {
        this.personImg = personImg;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }


}
