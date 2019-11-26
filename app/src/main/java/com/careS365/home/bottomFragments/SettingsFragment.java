package com.careS365.home.bottomFragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.AccountActivity;
import com.careS365.base.MyApp;
import com.careS365.contact.ContactActivity;
import com.careS365.editCircle.EditCircleActivity;
import com.careS365.home.HomeActivity;
import com.careS365.home.bottomFragments.presenter.IPSettingsFragment;
import com.careS365.home.bottomFragments.presenter.PSettingsFragment;
import com.careS365.login.LoginActivity;
import com.careS365.notification.NotificationActivity;
import com.careS365.privacyPolicy.PrivacyPolicyActivity;
import com.careS365.responseModel.LogoutResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.PreferenceHandler;
import com.careS365.util.Utility;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener,ISettingsFragment {

    Context context;
    @BindView(R.id.sp_bld_grp_signup)
    TextView tvCircle;
    @BindView(R.id.tv_notification)
    TextView tvNotification;
    @BindView(R.id.tv_edit_circle)
    TextView tvEditCircle;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.tv_privacy_policy)
    TextView tvPrivacyPolicy;

    IPSettingsFragment ipSettingsFragment;
    ProgressDialog progressDialog;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,view);
        context = getContext();
        ipSettingsFragment = new PSettingsFragment(this);
        init();
        return view;
    }

    private void init() {

        tvNotification.setTypeface(Utility.typeFaceForText(getContext()));
        tvEditCircle.setTypeface(Utility.typeFaceForText(getContext()));
        tvAccount.setTypeface(Utility.typeFaceForText(getContext()));
        tvLogout.setTypeface(Utility.typeFaceForText(getContext()));
        tvContactUs.setTypeface(Utility.typeFaceForText(getContext()));
        tvPrivacyPolicy.setTypeface(Utility.typeFaceForText(getContext()));

        tvNotification.setOnClickListener(this);
        tvEditCircle.setOnClickListener(this);
        tvAccount.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvContactUs.setOnClickListener(this);
        tvPrivacyPolicy.setOnClickListener(this);

        if(Constants.SELECTED_CIRCLE.equals(""))
            tvCircle.setText("Select Circle");
        else
            tvCircle.setText(Constants.circlesHashmap.get(Constants.SELECTED_CIRCLE));

        tvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.circlesHashmap.size()>0)
                    loadCircleSpinner();

            }
        });
    }

    private void loadCircleSpinner() {
        if(Constants.circlesHashmap.size()>0) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item,Constants.circlesArrayList);
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);

                    for(Map.Entry entry: Constants.circlesHashmap.entrySet()){
                        if(strName.equals(entry.getValue())){
                            Constants.SELECTED_CIRCLE = (String) entry.getKey();
                            break; //breaking because its one to one map
                        }
                    }

                    for (HashMap.Entry<String, HashMap<String,String>> entry : Constants.circlesMap.entrySet()) {
                        if(entry.getKey().equals(Constants.SELECTED_CIRCLE)){
                            Constants.selectedcircleMembersHashmap = entry.getValue();
                        }
                    }

                    tvCircle.setText(strName);
                    Log.e("selection text","" + tvCircle.getText().toString());

                    /*if(Utility.isNetworkConnected(context)){
                        progressDialog = Utility.showLoader(getContext());
                        ipMembersFragment.getCircleMembers(Constants.SELECTED_CIRCLE);
                    } else {
                        Toast.makeText(context, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }*/

                    //updateBottomsheetList();
                    /*for(Map.Entry entry: Constants.selectedcircleMembersHashmap.entrySet()){
                        subscribeToUpdates(entry.getKey().toString(),entry.getValue().toString());
                    }*/
                    dialog.dismiss();
                }
            });
            builderSingle.show();
        } else {
            Toast.makeText(context, "No Circle Found.Please create a new one", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_notification:
                startActivity(new Intent((HomeActivity)context, NotificationActivity.class));
                break;
            case R.id.tv_edit_circle:
                if(Constants.SELECTED_CIRCLE.equals("")) {
                    Toast.makeText(context, "Please Add Circle", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent((HomeActivity) context, EditCircleActivity.class));
                }
                break;
            case R.id.tv_account:
                startActivity(new Intent((HomeActivity)context, AccountActivity.class));
                break;
            case R.id.tv_logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Logout");
                alertDialogBuilder
                        .setMessage("" +
                                "\nAre you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                if(Utility.isNetworkConnected(context)){
                                    progressDialog = Utility.showLoader(getContext());
                                    ipSettingsFragment.logout(Utility.getUserId(),Utility.getAuthToken());
                                } else {
                                    Toast.makeText(context, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                /*Intent intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.tv_contact_us:
                startActivity(new Intent((HomeActivity)context, ContactActivity.class));
                break;
            case R.id.tv_privacy_policy:
                startActivity(new Intent((HomeActivity)context, PrivacyPolicyActivity.class));
                break;

        }
    }

    @Override
    public void logoutSuccessFromPresenter(LogoutResponseModel getCirclesResponseModel) {
        progressDialog.dismiss();
        new PreferenceHandler().clearSavedPrefrences(MyApp.getInstance().getApplicationContext());
        Constants.SELECTED_CIRCLE = "";
        Constants.circlesHashmap.clear();
        Constants.selectedcircleMembersHashmap.clear();
        Constants.circlesArrayList.clear();
        Constants.movedThroughLink = "0";
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void logoutFailureFromPresenter(String message) {
        progressDialog.dismiss();
        //Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }
}
