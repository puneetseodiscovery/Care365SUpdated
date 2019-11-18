package com.careS365.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHandler {

    public static final int MODE = Context.MODE_PRIVATE;
    public static final String PREF_NAME = "APPFRAMEWORK_PREFERENCES";
    public static final String REM_PREF_NAME = "REM_PREF_NAME";
    public static final String SAVE_IMG_PREF_NAME = "SAVE_IMG_PREF_NAME";

    public static final String PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";
    public static final String PREF_KEY_USER_PASSWORD = "PREF_KEY_USER_PASSWORD";
    public static final String PREF_KEY_LOGIN_TOKEN = "PREF_KEY_LOGIN_TOKEN";
    public static final String PREF_KEY_LOGIN_USER_ID = "PREF_KEY_LOGIN_USER_ID";
    public static final String PREF_KEY_LOGIN_AUTH_TOKEN = "PREF_KEY_LOGIN_AUTH_TOKEN";
    public static final String PREF_KEY_LOGIN_USER_NAME = "PREF_KEY_LOGIN_USER_NAME";
    public static final String PREF_KEY_USER_PHONE_NUMBER= "PREF_KEY_USER_PHONE_NUMBER";
    public static final String PREF_KEY_FIREBASE_TOKEN = "PREF_KEY_FIREBASE_TOKEN";
    public static final String PREF_KEY_UPLOAD_IMG = "PREF_KEY_UPLOAD_IMG";
    public static final String PREF_KEY_IMG_NAME = "PREF_KEY_IMG_NAME";

    public static final String PREF_KEY_RANGE_NOTIFY_STATUS = "PREF_KEY_RANGE_NOTIFY_STATUS";
    public static final String PREF_KEY_BATTERY_NOTIFY_STATUS = "PREF_KEY_BATTERY_NOTIFY_STATUS";
    public static final String PREF_KEY_USER_IMAGE = "PREF_KEY_USER_IMAGE";
    public static final String PREF_KEY_BATTERY_PERCENTAGE = "PREF_KEY_BATTERY_PERCENTAGE";


    public void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public void writeInteger(Context context, String key, int value) {
    getEditor(context).putInt(key, value).commit();
    }

    public int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public void writeREMString(Context context, String key, String value) {
        getREMEditor(context).putString(key, value).commit();
    }

    public String readREMString(Context context, String key, String defValue) {
        return getREMPREFERENCE(context).getString(key, defValue);
    }

    public void writeSaveImgString(Context context, String key, String value) {
        getSaveImgEditor(context).putString(key, value).commit();
    }

    public String readSaveImgString(Context context, String key, String defValue) {
        return getSaveImgPREFERENCE(context).getString(key, defValue);
    }

    public void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }


    public SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public SharedPreferences.Editor getREMEditor(Context context) {
        return getREMPREFERENCE(context).edit();
    }

    public SharedPreferences.Editor getSaveImgEditor(Context context) {
        return getSaveImgPREFERENCE(context).edit();
    }

    public SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public SharedPreferences getREMPREFERENCE(Context context) {
        return context.getSharedPreferences(REM_PREF_NAME, MODE);
    }

    public SharedPreferences getSaveImgPREFERENCE(Context context) {
        return context.getSharedPreferences(SAVE_IMG_PREF_NAME, MODE);
    }

    public void clearSavedPrefrences(Context context) {
        SharedPreferences settings= context.getSharedPreferences(PREF_NAME, MODE);
        settings.edit().clear().apply();
    }

    public void clearREMSavedPrefrences(Context context) {
        SharedPreferences settings= context.getSharedPreferences(REM_PREF_NAME, MODE);
        settings.edit().clear().apply();
    }

    public void clearSavedImgPrefrences(Context context) {
        SharedPreferences settings= context.getSharedPreferences(SAVE_IMG_PREF_NAME, MODE);
        settings.edit().clear().apply();
    }

}