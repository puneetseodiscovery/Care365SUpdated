package com.careS365.home.bottomFragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.careS365.home.bottomFragments.adapter.UsersAdapter;
import com.careS365.home.bottomFragments.homeBottomsheetControl.BottomSheetBehavior;
import com.careS365.home.bottomFragments.homeBottomsheetControl.BottomSheetBehaviorRecyclerManager;
import com.careS365.home.bottomFragments.model.BeanUser;
import com.careS365.home.bottomFragments.presenter.IPMembersFragment;
import com.careS365.home.bottomFragments.presenter.PMembersFragment;
import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.invitecode.ShareInviteCodeActivity;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.careS365.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersFragment extends Fragment implements IMembersFragment {

    ArrayList<BeanUser> userArrayList = new ArrayList<>();

    @BindView(R.id.tv_circle)
    TextView tvCircle;
    Context context;

    View rootView;

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private View mBottomSheetView;
    private CoordinatorLayout mParent;
    private RecyclerView mBottomSheetRecyclerRight;
    private LinearLayoutManager mLayoutManagerRight;
    private UsersAdapter mAdapterRight;
    private RelativeLayout mBottomSheetTrigger;
    private ImageView ivAddMember;

    IPMembersFragment ipMembersFragment;
    ProgressDialog progressDialog;

    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Double userLat, userLong;
    LatLng userLocation;
    String userAddress = "", userTime = "", userBatteryPer = "";
    Marker markerCurrentUser, markerCircleMember;
    ArrayList<String> mapPlottedUsers = new ArrayList<>();
    HashMap<String, String> hashmapAddress = new HashMap<>();
    HashMap<String, String> hashmapTime = new HashMap<>();
    HashMap<String, String> hashmapBattery = new HashMap<>();

    String get_userID="",get_circleID="";


    public MembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity();
        ipMembersFragment = new PMembersFragment(this);
        init();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {

        if (Constants.SELECTED_CIRCLE.equals("")) {
            tvCircle.setText("Select Circle");
        } else {
            tvCircle.setText(Constants.circlesHashmap.get(Constants.SELECTED_CIRCLE));
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        if (Utility.isNetworkConnected(context)) {
            progressDialog = Utility.showLoader(getContext());
            ipMembersFragment.getCircles(Utility.getUserId());
        } else {
            Toast.makeText(context, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Constants.currentUserLat = Double.parseDouble(getArguments().getString("currentLat"));
        Constants.currentUserLong = Double.parseDouble(getArguments().getString("currentLong"));

        tvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.circlesHashmap.size() > 0)
                    loadCircleSpinner();
                else
                    Toast.makeText(context, "No Circles Found.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateMap() {

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mMap.setMinZoomPreference(5);

                mMap.clear();

                subscribeToUpdates(Utility.getUserId(), Utility.getUserName());
                if (Constants.selectedcircleMembersHashmap.size() > 0) {
                    for (Map.Entry entry : Constants.selectedcircleMembersHashmap.entrySet()) {
                        subscribeToUpdates(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constants.currentUserLat,
                        Constants.currentUserLong), 15));
            }
        });
    }

    private void initBottomSheet(View rootView, Double userLat, Double userLong) {

        mParent = (CoordinatorLayout) rootView.findViewById(R.id.parent_container);
        mParent.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        mBottomSheetView = rootView.findViewById(R.id.main_bottomsheet);
        mBottomSheetTrigger = rootView.findViewById(R.id.bottomsheet_trigger);
        ivAddMember = rootView.findViewById(R.id.iv_add_member);


        mBottomSheetRecyclerRight = (RecyclerView) rootView.findViewById(R.id.btm_recyclerview_right);
        mLayoutManagerRight = new LinearLayoutManager(context);
        mBottomSheetRecyclerRight.setLayoutManager(mLayoutManagerRight);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetView);
        mBottomSheetBehavior.setPeekHeight(140);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (Constants.SELECTED_CIRCLE.equals("")) {
            ivAddMember.setVisibility(View.GONE);
        } else {
            ivAddMember.setVisibility(View.VISIBLE);
        }

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
        // ToDo: user adapter
        userArrayList.add(new BeanUser(userLat, userLong, Utility.getUserName(), hashmapAddress.get(Utility.getUserId()), hashmapTime.get(Utility.getUserId()), ""));
        mAdapterRight = new UsersAdapter(getActivity(),Utility.getUserId(), userArrayList,ipMembersFragment,get_circleID);
        mBottomSheetRecyclerRight.setAdapter(mAdapterRight);

        BottomSheetBehaviorRecyclerManager manager = new BottomSheetBehaviorRecyclerManager(mParent, mBottomSheetBehavior, mBottomSheetView);
        manager.addControl(mBottomSheetRecyclerRight);
        manager.create();

        mBottomSheetTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        ivAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ShareInviteCodeActivity.class));
            }
        });
    }

    public static Bitmap createCustomMarker(Context context, String firstLetter) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        TextView tvFirstLetterPerson = (TextView) marker.findViewById(R.id.tv_fl_person);
        tvFirstLetterPerson.setText(firstLetter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }


    private void subscribeToUpdates(String userId, String userName) {
        final String path = "locations/" + userId;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("datasnapshot id", "" + Utility.getUserId());
                if (dataSnapshot.getValue() != null) {
                    try {
                        setMarker(dataSnapshot, userId, userName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TAG", " it's null.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private LatLng setMarker(DataSnapshot dataSnapshot, String userId, String userName) {
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        userLat = Double.parseDouble(value.get("latitude").toString());
        userLong = Double.parseDouble(value.get("longitude").toString());
        if (Utility.getUserId().equals(userId)) {
            Constants.currentUserLat = userLat;
            Constants.currentUserLong = userLong;

        }
        userLocation = new LatLng(userLat, userLong);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(userLat, userLong))
                .zoom(16)
                .build();


        getAddress(userId);

        userTime = value.get("time").toString();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        userTime = df.format(value.get("time"));
        hashmapTime.put(userId, userTime);
        if (userId.equals(Utility.getUserId())) {
            if (!mapPlottedUsers.contains(userId)) {
                markerCurrentUser = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(userLat, userLong))
                                .title(userName)
                                .icon(BitmapDescriptorFactory.fromBitmap(
                                        createCustomMarker(context, userName.substring(0, 1))))
                );
                initBottomSheet(rootView, userLat, userLong);
                mapPlottedUsers.add(userId);
            } else {
                markerCurrentUser.setPosition(new LatLng(userLat, userLong));
                markerCurrentUser.setTitle(userName);
                updateBottomsheetList(userLat, userLong);
            }
        } else {
            if (!mapPlottedUsers.contains(userId)) {
                markerCircleMember = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(userLat, userLong))
                                .title(userName)
                                .icon(BitmapDescriptorFactory.fromBitmap(
                                        createCustomMarker(context, userName.substring(0, 1))))
                );
                updateBottomsheetList(userLat, userLong);
                mapPlottedUsers.add(userId);
            } else {
                markerCircleMember.setPosition(new LatLng(userLat, userLong));
                markerCircleMember.setTitle(userName);
                updateBottomsheetList(userLat, userLong);
            }
        }

        getBatteryPercentage(userId, userName, userLat, userLong);
        return userLocation;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getBatteryPercentage(String userId, String userName, Double userLat, Double userLong) {
        final String path = "battery/" + userId;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    try {
                        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                        hashmapBattery.put(userId, value.get("batteryPer").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TAG", " it's null.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAddress(String userId) {
        if (userLat != null && userLong != null) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(userLat, userLong, 1);
                if (addresses != null) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                    hashmapAddress.put(userId, strReturnedAddress.toString());

                } else {
                    Toast.makeText(getActivity(), "No Address returned!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Cannot get Address!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void loadCircleSpinner() {

        Log.e("circlesMap", "" + Constants.circlesMap);

        if (Constants.circlesHashmap.size() > 0) {

            mapPlottedUsers.clear();
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, Constants.circlesArrayList);

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);

                    for (Map.Entry entry : Constants.circlesHashmap.entrySet()) {
                        if (strName.equals(entry.getValue())) {
                            Constants.SELECTED_CIRCLE = (String) entry.getKey();

                            break; //breaking because its one to one map
                        }
                    }

                    for (HashMap.Entry<String, HashMap<String, String>> entry : Constants.circlesMap.entrySet()) {
                        if (entry.getKey().equals(Constants.SELECTED_CIRCLE)) {
                            Constants.selectedcircleMembersHashmap = entry.getValue();
                        }
                    }

                    tvCircle.setText(strName);

                    if (tvCircle.getText().equals("Select Circle"))
                        ivAddMember.setVisibility(View.GONE);
                    else
                        ivAddMember.setVisibility(View.VISIBLE);

                    updateMap();
                    //onMapReady(mMap);
                    dialog.dismiss();
                }
            });
            builderSingle.show();

            ivAddMember.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, "No Circle Found.Please create a new one", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBottomsheetList(Double userLat, Double userLong) {
        userArrayList.clear();
        for (HashMap.Entry<String, String> entry : Constants.selectedcircleMembersHashmap.entrySet()) {
            if (entry.getKey().equals(Utility.getUserId()))
                userArrayList.add(new BeanUser(userLat, userLong, entry.getValue(), hashmapAddress.get(entry.getKey()), hashmapTime.get(entry.getKey()), ""));
            else
                userArrayList.add(new BeanUser(userLat, userLong, entry.getValue(), hashmapAddress.get(entry.getKey()), hashmapTime.get(entry.getKey()), hashmapBattery.get(entry.getKey()) + "%"));
        }
        if (userArrayList.size() == 0) {
            userArrayList.add(new BeanUser(userLat, userLong, Utility.getUserName(), hashmapAddress.get(Utility.getUserId()), hashmapTime.get(Utility.getUserId()), ""));
        }
        mAdapterRight.notifyDataSetChanged();

    }

    @Override
    public void getCirclesSuccessFromPresenter(GetCirclesResponseModel getCirclesResponseModel) {
        progressDialog.dismiss();

        updateMap();

        Constants.circlesArrayList.clear();
        Constants.circlesHashmap.clear();
        Constants.circlesMap.clear();
        Constants.getCirclesResponseModel = getCirclesResponseModel;

        //ToDo: get circle id
        for (int m=0;m<getCirclesResponseModel.getData().size();m++){
            get_circleID=getCirclesResponseModel.getData().get(m).getId();
        }

        for (int i = 0; i < getCirclesResponseModel.getData().size(); i++) {
            HashMap<String, String> circleMembersMap = new HashMap<String, String>();
            for (int j = 0; j < getCirclesResponseModel.getData().get(i).getAllMembers().size(); j++) {
                circleMembersMap.put(getCirclesResponseModel.getData().get(i).getAllMembers().get(j).getUserId(), getCirclesResponseModel.getData().get(i).getAllMembers().get(j).getFullName());
                Log.e("" + i, "" + circleMembersMap);
            }
            circleMembersMap.put(getCirclesResponseModel.getData().get(i).getCreatedBy(), getCirclesResponseModel.getData().get(i).getCreatedByName());
            Constants.circlesMap.put(getCirclesResponseModel.getData().get(i).getId(), circleMembersMap);
            Constants.circlesArrayList.add(getCirclesResponseModel.getData().get(i).getCircleName());
            Constants.circlesHashmap.put(getCirclesResponseModel.getData().get(i).getId(), getCirclesResponseModel.getData().get(i).getCircleName());

        }

    }

    @Override
    public void getCirclesFailureFromPresenter(String message) {
        progressDialog.dismiss();
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
        updateMap();

    }

    @Override
    public void getCirclesNoDataFailureFromPresenter(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(context, "" + msgg, Toast.LENGTH_SHORT).show();
        updateMap();

    }

    @Override
    public void getCircleMembersSuccessFromPresenter(GetCircleMembersResponseModel getCircleMembersResponseModel) {
        progressDialog.dismiss();
    }

    @Override
    public void getCircleMembersFailureFromPresenter(String msggg) {
        progressDialog.dismiss();
        Toast.makeText(context, "" + msggg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendNotificationAllSuccessFromPresenter(BatteryLowNotificationResponseModel batteryLowNotificationResponseModel) {
        progressDialog.dismiss();
    }

    @Override
    public void sendNotificationAllFailedFromPresenter(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Constants.SELECTED_CIRCLE.equals("")) {
            tvCircle.setText("Select Circle");
        } else {
            tvCircle.setText(Constants.circlesHashmap.get(Constants.SELECTED_CIRCLE));
        }

    }
}
