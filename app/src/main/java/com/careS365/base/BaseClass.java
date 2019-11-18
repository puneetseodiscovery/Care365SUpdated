package com.careS365.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.careS365.util.PreferenceHandler;
import com.firebase.client.Firebase;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class BaseClass extends AppCompatActivity {

    public static String deviceToken;
    public static String firebaseUsername = "";
    public static String firebasePassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void getDeviceToken() {
        startService(new Intent(this, BaseFirebaseMessagingService.class));
        deviceToken = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, "");

    }
}
