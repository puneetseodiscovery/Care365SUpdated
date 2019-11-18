package com.careS365.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.presenter.IPResetPasswordActivity;
import com.careS365.account.presenter.PResetPasswordActivity;
import com.careS365.base.BaseClass;
import com.careS365.login.LoginActivity;
import com.careS365.responseModel.ResetPasswordResponseModel;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPasswordActivity extends BaseClass implements IResetPasswordActivity {

    @BindView(R.id.et_new_password)
    EditText etNewPass;
    @BindView(R.id.et_confirm_password)
    EditText etCnfrmPass;
    @BindView(R.id.btn_continue)
    Button btnContinue;

    IPResetPasswordActivity ipResetPasswordActivity;
    ProgressDialog progressDialog;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        ipResetPasswordActivity = new PResetPasswordActivity(this);
        userId = Utility.getUserId();
        Log.d("data++++", "" + userId);

    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        /*Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
    }

    public void onConitnueClicked(View view) {
        if (Utility.isNetworkConnected(this)) {
            if (etNewPass.getText().toString().length() > 0 && etCnfrmPass.getText().toString().length() > 0) {
                if (etNewPass.getText().toString().equals(etCnfrmPass.getText().toString())) {
                    progressDialog = Utility.showLoader(this);
                    ipResetPasswordActivity.resetPassword(etNewPass.getText().toString(), etCnfrmPass.getText().toString(), userId);
                } else {
                    Toast.makeText(this, "New Password and Confirm Password should be same.", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (etCnfrmPass.getText().toString().length() == 0) {
                    etCnfrmPass.setError("Please enter confirm password");
                    etCnfrmPass.requestFocus();
                }
                if (etNewPass.getText().toString().length() == 0) {
                    etNewPass.setError("Please enter password");
                    etNewPass.requestFocus();
                }
            }
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void resetPasswordSuccessFromPresenterToActivity(ResetPasswordResponseModel resetPasswordResponseModel) {
        progressDialog.dismiss();

        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void resetPasswordFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
