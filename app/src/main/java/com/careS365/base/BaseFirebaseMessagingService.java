package com.careS365.base;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.careS365.R;
import com.careS365.home.HomeActivity;
import com.careS365.util.PreferenceHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class BaseFirebaseMessagingService extends FirebaseMessagingService {

    String device_token;
    String apptitle = "", messagebody = "", user_Id = "", appIcon = "", messageSound = "";
    JSONObject jsonObject;

    public BaseFirebaseMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate", "Working");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("Firebase Token", device_token);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, device_token);
                        String mLoginToken = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, "");
                        Log.d("1mLoginToken", mLoginToken);

                    }
                });
    }

    @Override
    public void onNewToken(String device_token) {
        Log.e("onNewToken", "Working");
        this.device_token = device_token;
        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, device_token);
        String mLoginToken = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, "");
        Log.d(TAG, "Refreshed token: " + mLoginToken);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("data+++", "" + remoteMessage.getData());
        String data = remoteMessage.getData().get("message");

        Log.d("remotedata", data);

        try {
            jsonObject = new JSONObject(data);
            apptitle = jsonObject.getString("title");
            messagebody = jsonObject.getString("body");
            user_Id = jsonObject.getString("id_s");
            appIcon = jsonObject.getString("icon");
            messageSound = String.valueOf(jsonObject.get("sound"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendNotification(apptitle,
                         messagebody);

    }

    private void sendNotification(String apptitle,
                                  String messagebody) {

        Log.e("sendNotification", "Working");

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(apptitle)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(messagebody)
                .setContentIntent(pendingIntent)
                //.setStyle(style)
                //.setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(R.mipmap.ic_launcher_round,
                        "Dismiss", pendingIntent);

        notificationManager.notify(1, notificationBuilder.build());

    }
}
