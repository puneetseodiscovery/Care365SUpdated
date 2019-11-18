package com.careS365.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;

import com.careS365.R;
import com.careS365.base.MyApp;


public class Utility {


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean validEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean validMobile(String mobileNo) {
        return Patterns.PHONE.matcher(mobileNo).matches();
    }

    // need to remove below method

    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
//            if (event.getRawX() >= (locationText.getLeft() - locationText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
//                // your action here
//
//                return true;
//            }
        }
        return false;
    }

    // need to remove above method

    public static Typeface typeFaceForBoldText(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/micross_bold.ttf");
    }

    public static Typeface typeFaceForText(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/micross_regular.ttf");
    }

    /*public static String message(Response response) {
        try {
            ResponseBody responseBody = response.errorBody();
            return responseBody == null ? response.message() : responseBody.string();
        } catch (IOException invite_code_user_icon) {
            return response.message();
        }
    }
*/

    public static String getUserId()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_ID, "");
    }

    public static String getBatteryPercentage()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_BATTERY_PERCENTAGE, "");
    }

    public static String getAuthToken()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_AUTH_TOKEN, "");
    }

    public static String getUserName()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_NAME, "");
    }

    public static String getPhoneNumber()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_USER_PHONE_NUMBER, "");
    }

    public static String getRangeNotifyStatus()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_RANGE_NOTIFY_STATUS, "");
    }

    public static String getBatteryNotifyStatus()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_BATTERY_NOTIFY_STATUS, "");
    }

    public static String getUserImage()
    {
        return new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_USER_IMAGE, "");
    }

    /*public static ProgressDialog showLoader(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading..");
        try {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        catch (Exception invite_code_user_icon)
        {
            invite_code_user_icon.fillInStackTrace();
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.layout_progress);
        return progressDialog;
    }*/

    public static boolean isValidPassword(final String password) {
        String pattern= "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{4,8}$";
        return password.matches(pattern);

    }

    public static ProgressDialog showLoader(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading..");
        try {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        catch (Exception e)
        {
            e.fillInStackTrace();
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.layout_progress);
        return progressDialog;
    }

}
