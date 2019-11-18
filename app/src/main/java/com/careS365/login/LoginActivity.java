package com.careS365.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.base.MyApp;
import com.careS365.forgotpass.ForgotPasswordActivity;
import com.careS365.home.HomeActivity;
import com.careS365.home.TrackerLocationService;
import com.careS365.invitecode.EnterInviteCodeActivity;
import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;
import com.careS365.signup.SignUpActivity;
import com.careS365.util.Constants;
import com.careS365.util.PreferenceHandler;
import com.careS365.util.Utility;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseClass implements ILoginActivity {

    @BindView(R.id.et_phone_login)
    EditText etPhone;
    @BindView(R.id.et_password_login)
    EditText etPassword;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    @BindView(R.id.tv_already_acc)
    TextView tvAlreadyAcc;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;

    IPLoginActivity ipLoginActivity;
    ProgressDialog progressDialog;
    String device_token = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = LoginActivity.this;
        ipLoginActivity = new PLoginActivity(this);
        init();
        // getDeviceToken();

        //Toast.makeText(this, "Device Token:" + deviceToken, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        /*etPhone.setText("9463924817");
        etPassword.setText("Test@123");*/

        device_token = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_FIREBASE_TOKEN, "");
        // Toast.makeText(this, ""+device_token, Toast.LENGTH_SHORT).show();

        etPhone.setTypeface(Utility.typeFaceForText(this));
        etPassword.setTypeface(Utility.typeFaceForText(this));
        tvForgotPass.setTypeface(Utility.typeFaceForBoldText(this));
        tvAlreadyAcc.setTypeface(Utility.typeFaceForText(this));
        tvSignUp.setTypeface(Utility.typeFaceForText(this));
    }

    public void onForgotPasswordClicked(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public void onLoginClicked(View view) {
        if (Utility.isNetworkConnected(this)) {
            if (etPhone.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0) {
                progressDialog = Utility.showLoader(this);
                ipLoginActivity.login(etPhone.getText().toString(), etPassword.getText().toString(), device_token);
            } else {
                if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Enter P" +
                            "" +
                            "" +
                            "assword");
                    etPassword.requestFocus();
                }

                if (etPhone.getText().toString().length() == 0) {
                    etPhone.setError("Enter Phone");
                    etPhone.requestFocus();
                }
            }
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
        // startActivity(new Intent(this, HomeActivity.class));
    }


    public void onSignupClicked(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @Override
    public void loginSuccessFromPresenterToActivity(LoginResponseModel loginResponseModel) {
        progressDialog.dismiss();
        String userId = loginResponseModel.getData().getId().toString().trim();
        String password = loginResponseModel.getData().getId().toString().trim() + loginResponseModel.getData().getFullName().toString().trim();
        loginUsertoFirebase(userId, password);
        startService(new Intent(this, TrackerLocationService.class));

        if (Constants.movedThroughLink.equals("1"))
            startActivity(new Intent(this, EnterInviteCodeActivity.class));
        else
            startActivity(new Intent(this, HomeActivity.class));

    }

    @Override
    public void loginFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    private void loginUsertoFirebase(final String userId, final String password) {
        String url = "https://care365-29dbd.firebaseio.com/users.json";
        /*final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Loading...");
        pd.show();*/

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("null")) {
                    //Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                    Log.e("firebase login", "user not found");
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(userId)) {
                            Log.e("firebase login", "user not found");
                            // Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();
                        } else if (obj.getJSONObject(userId).getString("password").equals(password)) {
                            firebaseUsername = userId;
                            firebasePassword = password;
                            //startActivity(new Intent(LoginActivity.this, Users.class));
                        } else {
                            Log.e("firebase", "incorrect password");
                            //Toast.makeText(LoginActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                //pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }


    @Override
    public void loginOTPNotVerifiedFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        // showOTPDialog();
    }

    @Override
    public void verifyOTPSuccessFromPresenterToActivity(VerifyOTPResponseModel verifyOTPResponseModel) {
        progressDialog.dismiss();

    }

    @Override
    public void verifyOTPFailureFromPresenterToActivity(String msggg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msggg, Toast.LENGTH_SHORT).show();
    }

    private void showOTPDialog() {
        LayoutInflater factory = LayoutInflater.from(LoginActivity.this);
        final View otpDialogView = factory.inflate(R.layout.custom_otp_dialog, null);
        final AlertDialog otpDialog = new AlertDialog.Builder(this).create();
        otpDialog.setView(otpDialogView);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final PinView pinView = otpDialogView.findViewById(R.id.pv_otp);

        otpDialogView.findViewById(R.id.btn_submit_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("phone3:", etPhone.getText().toString());
                Log.e("otp3:", pinView.getText().toString());
                progressDialog = Utility.showLoader(LoginActivity.this);
                ipLoginActivity.verifyOTP(etPhone.getText().toString(), pinView.getText().toString());
            }
        });
        otpDialog.show();
    }
}
