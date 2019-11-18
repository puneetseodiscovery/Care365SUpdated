package com.careS365.deleteCircleMember;

public interface IMDeleteCircleMemberActivity {
    void getCircleMembers(String selectedCircle);
    void deleteCircleMembers(String selectedCircle, String userId, String selectedUsers);
}
