package com.careS365.deleteCircle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.DeleteCircleResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteCircleActivity extends BaseClass implements IDeleteCircleActivity {

    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.btn_no)
    Button btnNo;

    IPDeleteCircleActivity ipDeleteCircleActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_circle);
        ButterKnife.bind(this);
        ipDeleteCircleActivity = new PDeleteCircleActivity(this);
    }

    @OnClick({R.id.btn_yes, R.id.btn_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                if(Utility.isNetworkConnected(this)){
                    progressDialog = Utility.showLoader(this);
                    ipDeleteCircleActivity.deleteCircle(Utility.getUserId(), Constants.SELECTED_CIRCLE);
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
    public void deleteCircleSuccessFromPresenterToActivity(DeleteCircleResponseModel deleteCircleResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + deleteCircleResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
        Constants.circlesHashmap.remove(Constants.SELECTED_CIRCLE);
        Constants.circlesMap.remove(Constants.SELECTED_CIRCLE);
        Constants.selectedcircleMembersHashmap.clear();
        Constants.circlesArrayList.clear();
        for(Map.Entry entry: Constants.circlesHashmap.entrySet()){
            Constants.circlesArrayList.add(entry.getValue().toString());
        }
        Constants.SELECTED_CIRCLE = "";
        super.onBackPressed();
    }

    @Override
    public void deleteCircleFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
