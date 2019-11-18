package com.careS365.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.account.presenter.IPDeleteAccountActivity;
import com.careS365.account.presenter.PDeleteAccountActivity;
import com.careS365.base.BaseClass;
import com.careS365.base.MyApp;
import com.careS365.login.LoginActivity;
import com.careS365.responseModel.DeleteAccountResponseModel;
import com.careS365.util.PreferenceHandler;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteAccountActivity extends BaseClass implements IDeleteAccountActivity {

    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.btn_no)
    Button btnNo;

    IPDeleteAccountActivity ipDeleteAccountActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        ButterKnife.bind(this);
        ipDeleteAccountActivity = new PDeleteAccountActivity(this);
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        /*Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
    }

    @OnClick({R.id.btn_yes, R.id.btn_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                if(Utility.isNetworkConnected(this)){
                    progressDialog = Utility.showLoader(this);
                    ipDeleteAccountActivity.deleteAccount(Utility.getUserId());
                } else {
                    Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.btn_no:

                break;
        }
    }

    @Override
    public void deleteAccountSuccessFromPresenterToActivity(DeleteAccountResponseModel deleteAccountResponseModel) {
        progressDialog.dismiss();
        new PreferenceHandler().clearSavedPrefrences(MyApp.getInstance().getApplicationContext());
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void deleteAccountFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
