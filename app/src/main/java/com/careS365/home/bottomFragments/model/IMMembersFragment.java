package com.careS365.home.bottomFragments.model;

public interface IMMembersFragment {
    void getCircles(String userId);
    void getCircleMembers(String selectedCircle);

    void notifyAllMebers(String user_id, String circle_id,String battery_percentage);

}
