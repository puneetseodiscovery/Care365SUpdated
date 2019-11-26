package com.careS365.splash;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.base.BaseFirebaseMessagingService;
import com.careS365.base.MyApp;
import com.careS365.home.HomeActivity;
import com.careS365.invitecode.EnterInviteCodeActivity;
import com.careS365.util.Constants;
import com.careS365.util.PreferenceHandler;
import com.careS365.welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class SplashActivity extends BaseClass {

    String session;
    String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    //String inviteCode,invitedBy,invitedCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getIntent().getData() != null) {
            receiveFirebaseDynamicLink();
            Constants.movedThroughLink = "1";
        } else {
            Constants.movedThroughLink = "0";
        }
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                //getDeviceToken();
                // do your task.
                startService(new Intent(SplashActivity.this, BaseFirebaseMessagingService.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("+++++intent null:", "" + (getIntent().getData() == null));
                        session = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_AUTH_TOKEN, "");
                        if (session.length() > 0) {
                            if (Constants.movedThroughLink.equals("1"))
                                startActivity(new Intent(SplashActivity.this, EnterInviteCodeActivity.class));
                            else
                                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        } else {
                            Intent mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();

                        }

                    }
                }, 1000);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                finishAffinity();
            }
        });
    }

    private boolean appinstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new ActivityNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static void launchPlayStoreWithAppPackage(Context context, String packageName) {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.careS365&hl=en_IN" + packageName));
        context.startActivity(i);
    }

    private int receiveFirebaseDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
// Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {

                            deepLink = pendingDynamicLinkData.getLink();
                            Constants.inviteCode = deepLink.toString().substring(deepLink.toString().indexOf('=') + 1, deepLink.toString().indexOf('&'));
                            Constants.invitedBy = deepLink.toString().substring(deepLink.toString().indexOf('&') + 8, deepLink.toString().lastIndexOf('&'));
                            //invitedBy = deepLink.toString().substring(deepLink.toString().lastIndexOf('=') + 1,deepLink.toString().lastIndexOf('&'));
                            Constants.invitedCircle = deepLink.toString().substring(deepLink.toString().lastIndexOf('=') + 1);
//                            code = deepLink.toString().substring(deepLink.toString().lastIndexOf('/') + 1);

                            Log.d("+++++++link", "" + deepLink);
                            Log.d("+++++++code", "" + Constants.inviteCode);
                            Log.d("+++++++by", "" + Constants.invitedBy);
                            Log.d("+++++++circle", "" + Constants.invitedCircle);
                        }


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SplashActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.w("abc", "getDynamicLink:onFailure", e);
                    }
                });
        return 1;
    }
}
