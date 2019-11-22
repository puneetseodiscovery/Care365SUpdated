package com.careS365.forgotpass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.ResetPasswordActivity;
import com.careS365.base.BaseClass;
import com.careS365.login.LoginActivity;
import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;
import com.careS365.util.Utility;
import com.chaos.view.PinView;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseClass implements IForgotPasswordActivity {

    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    @BindView(R.id.tv_enter_your_mobile)
    TextView tvMobileText;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_phone_number)
    EditText et_phone_number;

    @BindView(R.id.btn_continue)
    Button btnConitnue;

    IPForgotPasswordActivity ipForgotPasswordActivity;
    ProgressDialog progressDialog;
    String phone_num = "";
    String phoneNumber = "", email = "";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        context = ForgotPasswordActivity.this;
        ipForgotPasswordActivity = new PForgotPasswordActivity(this);
        init();
    }

    private void init() {
        phone_num = Utility.getPhoneNumber();
        tvForgotPass.setTypeface(Utility.typeFaceForText(this));
        tvMobileText.setTypeface(Utility.typeFaceForText(this));
        et_email.setTypeface(Utility.typeFaceForText(this));
        btnConitnue.setTypeface(Utility.typeFaceForText(this));
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onContinueClicked(View view) {
        if (Utility.isNetworkConnected(this)) {
            if (et_email.getText().toString().trim().isEmpty()) {
                et_email.setError("Enter email ");
            } else if (et_phone_number.getText().toString().trim().isEmpty()) {
                et_phone_number.setError("Enter phone number ");
            } else {
                if (Utility.isNetworkConnected(context)) {
                    progressDialog = Utility.showLoader(context);
                    ipForgotPasswordActivity.forgotPass(et_phone_number.getText().toString(), et_email.getText().toString());
                }

            }

        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
        //startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onForgotPassSuccessFromPresenterToActivity(ForgotPasswordResponseModel forgotPasswordResponseModel) {
        progressDialog.dismiss();

        phoneNumber = forgotPasswordResponseModel.getData().getPhoneNum();
        email = forgotPasswordResponseModel.getData().getEmail();

        Toast.makeText(this, "" + forgotPasswordResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
        showOTPDialog();
    }

    @Override
    public void onForgotPassFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgotPassVerifyOTPSuccessFromPresenterToActivity(ForgotPassVerifyOTPResponseModel forgotPassVerifyOTPResponseModel) {
        progressDialog.dismiss();
        String get_User_ID = forgotPassVerifyOTPResponseModel.getData().getId();
        startActivity(new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class).putExtra("get_User_ID", get_User_ID));
    }

    @Override
    public void onForgotPassVerifyOTPFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        ;
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResendOtpSuccessResponseFromPrensenter(ResendOtpResponseModel resendOtpResponseModel) {
        progressDialog.dismiss();
    }

    @Override
    public void onResendOtpFailedResponseFromPrensenter(String message) {
        progressDialog.dismiss();
    }

    private void showOTPDialog() {

        Log.e("phone:", et_email.getText().toString());
        LayoutInflater factory = LayoutInflater.from(ForgotPasswordActivity.this);
        final View otpDialogView = factory.inflate(R.layout.custom_otp_dialog, null);
        final AlertDialog otpDialog = new AlertDialog.Builder(this).create();
        otpDialog.setView(otpDialogView);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final PinView pinView = otpDialogView.findViewById(R.id.pv_otp);

        SmsVerifyCatcher smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                //pinView.setText(getOTPfromMessage(message));
                Log.e("phone1:", et_email.getText().toString());
                Log.e("otp1:", pinView.getText().toString());
            }
        });

        otpDialogView.findViewById(R.id.btn_submit_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("phone3:", et_email.getText().toString());
                Log.e("otp3:", pinView.getText().toString());
                progressDialog = Utility.showLoader(ForgotPasswordActivity.this);
                ipForgotPasswordActivity.forgotPassVerifyOTP(et_phone_number.getText().toString(), pinView.getText().toString());
            }
        });
        otpDialogView.findViewById(R.id.tv_resend_Otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(context)) {
                    progressDialog = Utility.showLoader(context);
                    ipForgotPasswordActivity.forgotPass(et_phone_number.getText().toString(), et_email.getText().toString());
                }
            }
        });
        otpDialog.show();
    }
}
