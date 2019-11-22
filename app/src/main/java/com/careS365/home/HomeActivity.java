package com.careS365.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.careS365.util.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.home.bottomFragments.CreateCircleFragment;
import com.careS365.home.bottomFragments.MembersFragment;
import com.careS365.home.bottomFragments.MessagesFragment;
import com.careS365.home.bottomFragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseClass implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    int defaultTab = 0;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClientLocation;
    GoogleApiClient mGoogleApiClient;
    String currentLat, currentLong, currentAddress, batteryPer;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mGoogleApiClientLocation = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        defaultTab = getIntent().getIntExtra("DefaultTab", 0);
       /* if (defaultTab == 3) {
            navigation.getMenu().findItem(R.id.navigation_settings).setChecked(true);
            loadFragment(new SettingsFragment(),"0");
        }*/
        //firebaseUsername = String.valueOf(Utility.getUserId());
    }

    private boolean isGpsOn() {

        boolean isGpsOn = false;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isGpsOn = false;

        } else {
            isGpsOn = true;

        }
        return isGpsOn;
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.gps_disable))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.turn_on),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                dialog.cancel();
                            }
                        });
        /*alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_members:
                    loadFragment(new MembersFragment(), "1");
                    return true;
                case R.id.navigation_create_circle:
                    loadFragment(new CreateCircleFragment(), "0");
                    return true;
                case R.id.navigation_messages:
                    if (Constants.SELECTED_CIRCLE.equals("") || Constants.SELECTED_CIRCLE.equals("Select Circle"))
                        Toast.makeText(HomeActivity.this, "Please select any of the circle to start chatting.", Toast.LENGTH_SHORT).show();
                    else
                        loadFragment(new MessagesFragment(), "0");
                    return true;
                case R.id.navigation_settings:
                    loadFragment(new SettingsFragment(), "0");
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment, String isMemberFragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isMemberFragment.equalsIgnoreCase("1")) {
            Bundle args = new Bundle();
            args.putString("currentLat", currentLat);
            args.putString("currentLong", currentLong);
            //args.putString("currentAddress", currentAddress);
            fragment.setArguments(args);
        }
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();

        /*if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClientLocation.connect();
        if (!isGpsOn()) {
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClientLocation);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClientLocation, mLocationRequest, this);
        } else {

            if (String.valueOf(location.getLatitude()).isEmpty()) {
                Toast.makeText(this, "location null", Toast.LENGTH_SHORT).show();
            } else {
                currentLat = String.valueOf(location.getLatitude());
                currentLong = String.valueOf(location.getLongitude());
                //getAddress();
                startService(new Intent(this, TrackerLocationService.class).putExtra("batteryPer", batteryPer));

                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                if (defaultTab == 0)
                    loadFragment(new MembersFragment(), "1");
            }

        }

    }

    /*private void getAddress() {
        if(currentLat!=null && currentLong!=null){
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(currentLat), Double.parseDouble(currentLong), 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    currentAddress = strReturnedAddress.toString();
                    //Log.w("My Current loction address", strReturnedAddress.toString());
                } else {
                    Toast.makeText(this, "No Address returned!", Toast.LENGTH_SHORT).show();
                    // Log.w("My Current loction address", "No Address returned!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Canont get Address!", Toast.LENGTH_SHORT).show();
                //Log.w("My Current loction address", "Canont get Address!");
            }
        }
    }*/

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        /*if (!location.hasAccuracy()) {
            return;
        }*/
        if (location.getAccuracy() > 5) {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            super.onBackPressed();
        }
    }
}
