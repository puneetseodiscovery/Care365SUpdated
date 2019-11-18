package com.careS365.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.presenter.IPEditPhoneNumberActivity;
import com.careS365.account.presenter.PEditPhoneNumberActivity;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.EditPhoneResponseModel;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPhoneNumberActivity extends BaseClass implements IEditPhoneNumberActivity {

    @BindView(R.id.et_phone_number)
    EditText etPhone;
    IPEditPhoneNumberActivity ipEditPhoneNumberActivity;
    ProgressDialog progressDialog;
    String user_ID = "", user_PhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);
        ButterKnife.bind(this);
        ipEditPhoneNumberActivity = new PEditPhoneNumberActivity(this);
        user_ID = Utility.getUserId();
        user_PhoneNumber = Utility.getPhoneNumber();
        etPhone.setText(user_PhoneNumber);
        etPhone.setEnabled(false);


    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
//        Intent intent = new Intent(this, AccountActivity.class);
//        startActivity(intent);
        super.onBackPressed();
    }

    public void onSaveClicked(View view) {
        if (etPhone.getText().toString().length() > 0) {
            progressDialog = Utility.showLoader(this);
            ipEditPhoneNumberActivity.editPhone(etPhone.getText().toString(), user_ID);
        } else {
            etPhone.setError("Enter phone number");
            etPhone.requestFocus();
        }
    }

    @Override
    public void editPhoneSuccessFromPresenterToActivity(EditPhoneResponseModel editPhoneResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + editPhoneResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editPhoneFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
