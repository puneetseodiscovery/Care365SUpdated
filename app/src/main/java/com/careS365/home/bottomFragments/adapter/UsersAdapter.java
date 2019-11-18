package com.careS365.home.bottomFragments.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.careS365.R;
import com.careS365.home.bottomFragments.model.BeanUser;
import com.careS365.home.bottomFragments.presenter.IPMembersFragment;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    Context context;
    ArrayList<BeanUser> list;
    IPMembersFragment ipMembersFragment;
    String get_circleID;
    String userId;


    public UsersAdapter(Context context, String userId, ArrayList<BeanUser> list, IPMembersFragment ipMembersFragment, String get_circleID) {
        this.context = context;
        this.ipMembersFragment = ipMembersFragment;
        this.get_circleID = get_circleID;
        this.userId = userId;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_user_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvUsername.setText(list.get(position).getUsername());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvBatteryPer.setText(list.get(position).getBatteryPer());
        Log.d("Battery++", "" + list.get(position).getBatteryPer());
        String batteryLevel = list.get(position).getBatteryPer();
        Log.d("btry+++", "" + batteryLevel);
        if (batteryLevel != null) {
            if (batteryLevel.endsWith("%")) {
                String batteryValue = String.valueOf(new StringBuffer(batteryLevel).deleteCharAt(batteryLevel.length() - 1));
                if (batteryValue.equals("10")) {
                    if (Utility.isNetworkConnected(context)) {
                        ipMembersFragment.notifyAllMebers(userId, get_circleID, batteryValue);

                    } else {
                        Toast.makeText(context, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();

                    }
                }
            } else {

            }
        }

        //ToDo implement notification api for battery
        holder.ivDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirections(list.get(position).getUserLat(), list.get(position).getUserLong());
            }
        });

        if (holder.tvBatteryPer.getText().toString().equals("")) {
            holder.llBattery.setVisibility(View.GONE);
            holder.ivDirections.setVisibility(View.GONE);
        } else {
            holder.llBattery.setVisibility(View.VISIBLE);
            holder.ivDirections.setVisibility(View.VISIBLE);
        }
        holder.llUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFragment(list.get(position).getServiceName(),list.get(position).getId());
            }
        });
    }

    public void getDirections(Double userLat, Double userLong) {
        /*String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+loginUserLat+","+loginUserLong+"&daddr="+userLat+","+userLong;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(Intent.createChooser(intent, "Select an application"));*/
        Log.e("userLat", "" + userLat);
        Log.e("userLong", "" + userLong);

        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + Constants.currentUserLat + "," + Constants.currentUserLong + "&daddr=" + userLat + "," + userLong);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_battery_percentage)
        TextView tvBatteryPer;
        @BindView(R.id.iv_directions)
        ImageView ivDirections;
        @BindView(R.id.ll_battery)
        LinearLayout llBattery;
        @BindView(R.id.ll_bs_user)
        LinearLayout llUserLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
        }

        public void init() {
        }
    }

    public void updateUserList(ArrayList<BeanUser> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
