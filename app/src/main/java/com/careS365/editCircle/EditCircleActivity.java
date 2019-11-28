package com.careS365.editCircle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.deleteCircle.DeleteCircleActivity;
import com.careS365.deleteCircleMember.DeleteCircleMemberActivity;
import com.careS365.invitecode.ShareInviteCodeActivity;
import com.careS365.leaveCircle.LeaveCircleActivity;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCircleActivity extends BaseClass {

    @BindView(R.id.tv_edit_circle)
    LinearLayout tvEditCircle;
    @BindView(R.id.tv_delete_circle_member)
    LinearLayout tvDeleteCircleMember;
    @BindView(R.id.tv_delete_circle)
    LinearLayout tvDeleteCircle;
    boolean creatorOfCircle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_circle);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        if (Constants.getCirclesResponseModel !=null){
            for(int i=0;i< Constants.getCirclesResponseModel.getData().size();i++){
                if(Constants.SELECTED_CIRCLE.equals(Constants.getCirclesResponseModel.getData().get(i).getId())
                        && Utility.getUserId().equals(Constants.getCirclesResponseModel.getData().get(i).getCreatedBy())){
                    creatorOfCircle = true;
                }
            }
        }


        if(creatorOfCircle){
            tvEditCircle.setVisibility(View.VISIBLE);
            tvDeleteCircleMember.setVisibility(View.VISIBLE);
            tvDeleteCircle.setVisibility(View.VISIBLE);
        } else {
            tvEditCircle.setVisibility(View.GONE);
            tvDeleteCircleMember.setVisibility(View.GONE);
            tvDeleteCircle.setVisibility(View.GONE);
        }
    }


    public void OnEditCircleNameClciked(View view) {
        startActivity(new Intent(this,EditCircleNameActivity.class));
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        /*Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("DefaultTab", 3);
        startActivity(intent);*/
        super.onBackPressed();
    }

    public void OnAddCircleMemeberClicked(View view) {
        startActivity(new Intent(this, ShareInviteCodeActivity.class));
    }

    public void onDeleteCircleMemberClicked(View view) {
        startActivity(new Intent(this, DeleteCircleMemberActivity.class));
    }

    public void onLeaveCircleClciked(View view) {
        startActivity(new Intent(this, LeaveCircleActivity.class));
    }

    public void OnDeleteCircleClicked(View view) {
        startActivity(new Intent(this, DeleteCircleActivity.class));
    }
}
