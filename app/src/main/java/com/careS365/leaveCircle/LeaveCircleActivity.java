package com.careS365.leaveCircle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.home.HomeActivity;
import com.careS365.responseModel.LeaveCircleResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeaveCircleActivity extends BaseClass implements ILeaveCircleActivity {

    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.btn_no)
    Button btnNo;

    IPLeaveCircleActivity ipLeaveCircleActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_circle);
        ButterKnife.bind(this);
        ipLeaveCircleActivity = new PLeaveCircleActivity(this);
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        /*Intent intent = new Intent(this, EditCircleActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
    }

    @OnClick({R.id.btn_yes, R.id.btn_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                    if(Utility.isNetworkConnected(this)){
                        progressDialog = Utility.showLoader(this);
                        ipLeaveCircleActivity.leaveCircle(Utility.getUserId(), Constants.SELECTED_CIRCLE);
                    } else {
                        Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                break;
            case R.id.btn_no:
                super.onBackPressed();
                break;
        }
    }

    @Override
    public void leaveCircleSuccessFromPresenterToActivity(LeaveCircleResponseModel leaveCircleResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + leaveCircleResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void leaveCircleFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void leaveCircleOwnerFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }
}
