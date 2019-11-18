package com.careS365.deleteCircleMember;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteCircleMemberActivity extends BaseClass implements IDeleteCircleMemberActivity {

    @BindView(R.id.rv_delete_circle_member)
    RecyclerView rvDeleteCircleMember;
    ArrayList<BeanDeleteCircleMember> arrayList ;
    String selectedUsers;

    IPDeleteCircleMemberActivity ipDeleteCircleMemberActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_circle_member);
        ButterKnife.bind(this);
        ipDeleteCircleMemberActivity = new PDeleteCircleMemberActivity(this);
        init();
    }

    private void init() {
        arrayList=new ArrayList<>();
        if(Utility.isNetworkConnected(this)) {
            progressDialog = Utility.showLoader(this);
            ipDeleteCircleMemberActivity.getCircleMembers(Constants.SELECTED_CIRCLE);
        } else
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();

        /*arrayList=new ArrayList<>();
        for(Map.Entry entry: Constants.selectedcircleMembersHashmap.entrySet()){
            if(entry.getKey().equals(Utility.getUserId())){
                //circleMembersList.put(entry.getKey().toString(),entry.getValue().toString());
            } else {
                arrayList.add(new BeanDeleteCircleMember(entry.getKey().toString(),"",entry.getValue().toString()));
            }
        }*/



        /*arrayList=new ArrayList<>();

        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Sapna"));
        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Puneet"));
        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Taran"));
        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Atul"));
        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Mohit"));
        arrayList.add(new BeanDeleteCircleMember(R.mipmap.messages_profile_pic,"Gagan"));*/

        rvDeleteCircleMember.setLayoutManager(new LinearLayoutManager(this));
        DeleteCircleMemberAdapter adapter = new DeleteCircleMemberAdapter(this, arrayList);
        rvDeleteCircleMember.setAdapter(adapter);
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        /*Intent intent = new Intent(this, EditCircleActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
    }

    public void getSelectedMembers(String users){
        selectedUsers = users;
    }


    public void onDeleteClicked(View view) {
        if(selectedUsers.length() > 0){
            if(Utility.isNetworkConnected(this)) {
                progressDialog = Utility.showLoader(this);
                ipDeleteCircleMemberActivity.deleteCircleMembers(Constants.SELECTED_CIRCLE,Utility.getUserId(),selectedUsers);
            } else
                Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please select circle members to delete.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getCircleMembersSuccessFromPresenterToActivity(GetCircleMembersResponseModel getCircleMembersResponseModel) {
        progressDialog.dismiss();
        for(int i=0;i<getCircleMembersResponseModel.getData().size();i++){
            arrayList.add(new BeanDeleteCircleMember(getCircleMembersResponseModel.getData().get(i).getUserId(),"",getCircleMembersResponseModel.getData().get(i).getUserId()));
        }
    }

    @Override
    public void getCircleMembersFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteCircleMembersSuccessFromPresenterToActivity(DeleteCircleMembersResponseModel deleteCircleMembersResponseModel) {
        progressDialog.dismiss();

    }

    @Override
    public void deleteCircleMembersFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
