package com.careS365.home;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careS365.util.Utility;
import com.firebase.client.Firebase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class TrackerLocationService extends Service {

    private static final String TAG = TrackerLocationService.class.getSimpleName();
    String batteryPer;
    Context context;

    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public void onCreate() {
        super.onCreate();
        //buildNotification();
        //loginToFirebase();
        //getBatteryPercentage();
        context = TrackerLocationService.this;
        requestLocationUpdates();
        Firebase.setAndroidContext(this);
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };



    private void requestLocationUpdates() {
        Log.d(TAG, "requestLocationUpdates");
        LocationRequest request = new LocationRequest();
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        /*int random = new Random().nextInt(75) + 100;
        Log.e(TAG,"random"+ random);*/
        //final String path = getString(R.string.firebase_path) + "/" + getString(R.string.transport_id);
        final String path = "locations/" + Utility.getUserId();
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                        ref.setValue(location);
                    }

                    //addBatteryNode(path);
                    getBatteryPercentage();
                    updateBatteryPercentage();

                   /* //TODO : Code was commented
                    Date date = new Date();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted = format1.format(date);
                    Log.e("Current Date","" + formatted);
                    String s = getTimeOfDay(date);

                    if(s.equals("1") || s.equals("2")){
                        updateWeeklyReportLocation(formatted,s);
                    }*/


                }
            }, null);

        }
    }

    private void updateWeeklyReportLocation(String date,String str) {
        LocationRequest request = new LocationRequest();
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        /*int random = new Random().nextInt(75) + 100;
        Log.e(TAG,"random"+ random);*/
        //final String path = getString(R.string.firebase_path) + "/" + getString(R.string.transport_id);

        final String path1 = "weeklyDriveReport/" + Utility.getUserId();
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_path));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path1);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                Log.e("count of children","" + count);
                if(count<7){
                    final String path;
                    if(str.equals("1"))
                        path = "weeklyDriveReport/" + Utility.getUserId() + "/" + date + "/startOfDay";
                    else
                        path = "weeklyDriveReport/" + Utility.getUserId() + "/" + date + "/endOfDay";
                    int permission = ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permission == PackageManager.PERMISSION_GRANTED) {
                        // Request location updates and when an update is
                        // received, store the location in Firebase
                        client.requestLocationUpdates(request, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                                Location location = locationResult.getLastLocation();
                                if (location != null) {
                                    Log.d(TAG, "location update " + location);
                                    ref.setValue(location);
                                }
                            }
                        }, null);
                    }
                } else {
                    Toast.makeText(TrackerLocationService.this, "You can't add more", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getTimeOfDay(Date date) {
        Log.e("Current Time of the day","" +getCurrentTimeOfDay(date));
        Log.e("Start of the day","" +atStartOfDay(date));
        Log.e("End of the day","" +atEndOfDay(date));
        if(getCurrentTimeOfDay(date).equals(atStartOfDay(date))){
            return "1";
        } else if(getCurrentTimeOfDay(date).equals(getCurrentTimeOfDay(date))){
            return "2";
        } else {
            return "0";
        }
    }

    public Date getCurrentTimeOfDay(Date date){
        //final Date date = new Date(); // your date
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.get(Calendar.YEAR);
        cal.get(Calendar.MONTH);
        cal.get(Calendar.DAY_OF_MONTH);
        cal.get(Calendar.HOUR_OF_DAY);
        cal.get(Calendar.MINUTE);
        cal.get(Calendar.SECOND);
        return cal.getTime();
    }

    static public Date getStartOfADay(Date day) {
        final long oneDayInMillis = 24 * 60 * 60 * 1000;
        return new Date(day.getTime() / oneDayInMillis * oneDayInMillis);
    }

    static public Date getEndOfADay(Date day) {
        final long oneDayInMillis = 24 * 60 * 60 * 1000;
        return new Date((day.getTime() / oneDayInMillis + 1) * oneDayInMillis - 1);
    }

    public Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    private void updateBatteryPercentage() {
        String url = "https://care365-29dbd.firebaseio.com/battery.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://care365-29dbd.firebaseio.com/battery");

                if(s.equals("null")) {
                    reference.child(Utility.getUserId()).child("batteryPer").setValue(batteryPer);
                }
                else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(Utility.getUserId())) {
                            reference.child(Utility.getUserId()).child("batteryPer").setValue(batteryPer);
                            //Toast.makeText(SignUpActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                        } else {
                            reference.child(Utility.getUserId()).child("batteryPer").setValue(batteryPer);
                            //Log.e("firebase","battery data updated");
                            //Toast.makeText(SignUpActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //pd.dismiss();
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError );
                //pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(request);
    }

    void getBatteryPercentage()
    {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;
        float p = batteryPct * 100;

        //Log.d("Battery percentage",String.valueOf(Math.round(p)));
        batteryPer = String.valueOf(Math.round(p));
        //Toast.makeText(this, "Battery percentage:" + String.valueOf(Math.round(p)), Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand (Intent intent, int flags, int startId) {
        /*batteryPer = intent.getStringExtra("batteryPer");
        Toast.makeText(this, "battery:" + batteryPer, Toast.LENGTH_SHORT).show();*/
        return START_STICKY;
    }

    public interface Callbacks{
        public void get(long data);
    }
}
