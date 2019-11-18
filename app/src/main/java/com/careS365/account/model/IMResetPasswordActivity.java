package com.careS365.account.model;

public interface IMResetPasswordActivity {
    void resetPassword(String newPass, String cnfrmPass, String userId);
}
