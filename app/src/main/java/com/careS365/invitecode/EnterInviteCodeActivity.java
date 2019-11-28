package com.careS365.invitecode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.home.HomeActivity;
import com.careS365.responseModel.JoinCircleResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;
import com.chaos.view.PinView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterInviteCodeActivity extends BaseClass implements IEnterInviteCodeActivity {


    @BindView(R.id.pv_otp)
    PinView etInviteCode;

    IPEnterInviteCodeActivity ipEnterInviteCodeActivity;
    ProgressDialog progressDialog;
    String myInviteCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_invite_code);
        ButterKnife.bind(this);

        Constants.movedThroughLink = "0";
        ipEnterInviteCodeActivity = new PEnterInviteCodeActivity(this);
//        if(Constants.inviteCode.length()>0)
            etInviteCode.setText(Constants.inviteCode);
    }

    public void onSubmitButtonClicked(View view){
        if(Utility.isNetworkConnected(this)){
            if(etInviteCode.getText().length()>0) {
                progressDialog = Utility.showLoader(this);
                ipEnterInviteCodeActivity.joinCircle(Constants.inviteCode,Constants.invitedBy,Constants.invitedCircle,Utility.getUserId());
            } else
                Toast.makeText(this, "invite code null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onCreateCircleClicked(View view){
        startActivity(new Intent(EnterInviteCodeActivity.this, HomeActivity.class));
    }

    @Override
    public void joinCircleSuccessFromPresenterToActivity(JoinCircleResponseModel joinCircleResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + joinCircleResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,HomeActivity.class));
    }

    @Override
    public void joinCircleFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void joinCircleWrongDataFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }
}
