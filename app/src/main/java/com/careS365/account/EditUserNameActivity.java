package com.careS365.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.presenter.IPEditUserNameActivity;
import com.careS365.account.presenter.PEditUserNameActivity;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.EditUsernameResponseModel;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserNameActivity extends BaseClass implements IEditUserNameActivity {

    @BindView(R.id.et_user_name)
    EditText etUsername;
    IPEditUserNameActivity ipEditUserNameActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);
        ButterKnife.bind(this);
        ipEditUserNameActivity = new PEditUserNameActivity(this);
        init();
    }

    private void init() {
        etUsername.setText(Utility.getUserName());
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        super.onBackPressed();
    }

    public void onSaveClicked(View view) {
        if (etUsername.getText().toString().length() > 0) {
            progressDialog = Utility.showLoader(this);
            ipEditUserNameActivity.editUsername(etUsername.getText().toString(), Utility.getUserId());
        } else {
            etUsername.setError("Enter username");
            etUsername.requestFocus();
        }
    }

    @Override
    public void editUsernameSuccessFromPresenterToActivity(EditUsernameResponseModel editUsernameResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + editUsernameResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editUsernameFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
