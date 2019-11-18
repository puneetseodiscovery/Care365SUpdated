package com.careS365.editCircle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.editCircle.presenter.IPEditCircleNameActivity;
import com.careS365.editCircle.presenter.PEditCircleNameActivity;
import com.careS365.responseModel.EditCircleNameResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCircleNameActivity extends BaseClass implements IEditCircleNameActivity {

    @BindView(R.id.et_circle_name)
    EditText etCircleName;


    IPEditCircleNameActivity ipEditCircleNameActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_circle_name);
        ButterKnife.bind(this);
        ipEditCircleNameActivity = new PEditCircleNameActivity(this);
        init();
    }

    private void init() {
        etCircleName.setText(Constants.circlesHashmap.get(Constants.SELECTED_CIRCLE));
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        super.onBackPressed();
    }

    public void onSaveClicked(View view) {
        if(Utility.isNetworkConnected(this)){
            if(etCircleName.getText().toString().length() > 0) {
                progressDialog = Utility.showLoader(this);
                ipEditCircleNameActivity.saveCircleName(etCircleName.getText().toString().trim(), Constants.SELECTED_CIRCLE);
            } else {
                etCircleName.setError("Please enter name of the circle.");
                etCircleName.requestFocus();
            }
        } else {

        }
    }

    @Override
    public void editCircleNameSuccessFromPresenterToActivity(EditCircleNameResponseModel editCircleNameResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + editCircleNameResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editCircleNameFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
