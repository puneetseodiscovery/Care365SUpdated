package com.careS365.allowaccess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.home.HomeActivity;

import butterknife.BindView;

public class AllowAccessActivity extends BaseClass {

    @BindView(R.id.tv_access_text)
    TextView tvAccessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);
        init();
    }

    private void init() {
        //tvAccessText.setTypeface(Utility.typeFaceForBoldText(this));
    }

    public void onAllowAccessClicked(View view){
        startActivity(new Intent(this, HomeActivity.class));
    }
}
