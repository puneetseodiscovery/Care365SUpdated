package com.careS365.driveReport;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.completeMap.CompleteMapsActivity;
import com.careS365.util.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriveReportAdapter extends RecyclerView.Adapter<DriveReportAdapter.ViewHolder> {

    Context context;
    ArrayList<BeanDriveReport> list;

    public DriveReportAdapter(Context context, ArrayList<BeanDriveReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_drive_report_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvDay.setText(list.get(position).getDay());
        holder.tvLocationStay.setText(list.get(position).getLocationStay());
        holder.tvLocationTime.setText(list.get(position).getLocationTime());
        holder.tvDriveDistance.setText(list.get(position).getDriveDistance());
        holder.tvDriveTime.setText(list.get(position).getDriveTime());

        holder.bindView(position);

        /*holder.mapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(context, "Map clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_location_stay)
        TextView tvLocationStay;
        @BindView(R.id.tv_location_time)
        TextView tvLocationTime;
        @BindView(R.id.tv_drive_distance)
        TextView tvDriveDistance;
        @BindView(R.id.tv_drive_time)
        TextView tvDriveTime;
        MapView mapView;
        GoogleMap map;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mapView = view.findViewById(R.id.lite_listrow_map);

            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to+ receive the GoogleMap object
                mapView.getMapAsync(this);
            }

            init();
        }

        public void init() {

            tvDay.setTypeface(Utility.typeFaceForText(context));
            tvLocationStay.setTypeface(Utility.typeFaceForText(context));
            tvLocationTime.setTypeface(Utility.typeFaceForText(context));
            tvDriveDistance.setTypeface(Utility.typeFaceForText(context));
            tvDriveTime.setTypeface(Utility.typeFaceForText(context));
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context);
            map = googleMap;
            setMapLocation();

            /*map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    context.startActivity(new Intent(context,CompleteMapsActivity.class));
                    return false;
                }
            });*/

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    context.startActivity(new Intent(context,CompleteMapsActivity.class));
                }
            });
        }

        private void setMapLocation() {
            if (map == null) return;

            // Add a marker for this item and set the camera
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.920455, 18.466941), 13f));
            map.addMarker(new MarkerOptions().position(new LatLng(-33.920455, 18.466941)));

            // Set the map type back to normal.
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        private void bindView(int pos) {
            setMapLocation();
        }

    }







    /*public DriveReportAdapter(Context context, ArrayList<BeanDriveReport> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_drive_report_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
       *//* Picasso.get().load(list.get(position).getPersonImg()).into(holder.ivProfilePic);
        // holder.ivCat.setImageResource(list.get(position).getServiceImage());
        holder.tvName.setText(list.get(position).getPersonName());
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvMessage.setText(list.get(position).getMessage());*//*
        *//*holder.llCircleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFragment(list.get(position).getServiceName(),list.get(position).getId());
            }
        });*//*
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_location_stay)
        TextView tvLocationStay;
        @BindView(R.id.tv_location_time)
        TextView tvLocationTime;
        @BindView(R.id.tv_drive_distance)
        TextView tvDriveDistance;
        @BindView(R.id.tv_drive_time)
        TextView tvDriveTime;

        //frg

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
        }

        public void init() {
            *//*tvDay.setTypeface(Utility.typeFaceForText(context));
            tvLocationStay.setTypeface(Utility.typeFaceForText(context));
            tvLocationTime.setTypeface(Utility.typeFaceForText(context));
            tvDriveDistance.setTypeface(Utility.typeFaceForText(context));
            tvDriveTime.setTypeface(Utility.typeFaceForText(context));*//*


            SupportMapFragment mapFragment = (SupportMapFragment) ((FragmentActivity)context).getSupportFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    mMap.clear(); //clear old markers

                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(37.4219999,-122.0862462))
                            .zoom(10)
                            .bearing(0)
                            .tilt(45)
                            .build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

                *//*mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4219999, -122.0862462))
                        .title("Spider Man")
                        .icon(bitmapDescriptorFromVector(getActivity(),R.mipmap.ic_launcher_round)));*//*

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(37.4629101,-122.2449094))
                            .title("Iron Man")
                            .snippet("His Talent : Plenty of money"));

                *//*mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293,-122.1136845))
                        .title("Captain America"));*//*
                }
            });

        }
    }*/

}
