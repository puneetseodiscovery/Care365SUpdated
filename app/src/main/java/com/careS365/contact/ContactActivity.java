package com.careS365.contact;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends BaseClass implements IContactActivity {

    @BindView(R.id.et_email)
    TextView tvEmail;

    @BindView(R.id.et_message)
    EditText etMsg;

    IPContactActivity ipContactActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        ipContactActivity = new PContactActivity(this);
        init();

    }

    private void init() {
        if (Utility.isNetworkConnected(this)) {
            progressDialog = Utility.showLoader(this);
            ipContactActivity.getAdminEmail();
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onBackClicked(View view) {
        super.onBackPressed();
    }

    public void onSendClicked(View view) {
        if (Utility.isNetworkConnected(this)) {
            if (etMsg.getText().toString().length() > 0) {
                progressDialog = Utility.showLoader(this);
                ipContactActivity.contactUs(tvEmail.getText().toString(),etMsg.getText().toString());
            } else {
                etMsg.requestFocus();
                etMsg.setError("Enter your query.");
            }
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void getAdminEmailSuccessFromPresenterToActivity(GetAdminEmailResponseModel getAdminEmailResponseModel) {
        progressDialog.dismiss();
        Log.e("admin email","" + getAdminEmailResponseModel.getData().getAdminEmail());
        tvEmail.setText(getAdminEmailResponseModel.getData().getAdminEmail());
    }

    @Override
    public void getAdminEmailFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void contactUsSuccessFromPresenterToActivity(ContactUsReponseModel contactUsReponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + contactUsReponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void contactUsFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }
}
