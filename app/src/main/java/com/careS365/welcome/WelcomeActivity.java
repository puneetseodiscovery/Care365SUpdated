package com.careS365.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.login.LoginActivity;
import com.careS365.signup.SignUpActivity;
import com.careS365.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseClass {

    @BindView(R.id.tv_welcome_text)
    TextView tvWelcomeText;
    @BindView(R.id.tv_welcome_text1)
    TextView tvWelcomeText1;
    @BindView(R.id.btn_get_started)
    Button btnGetStarted;
    @BindView(R.id.tv_already_acc)
    TextView tvAlreadyAcc;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvWelcomeText.setTypeface(Utility.typeFaceForBoldText(this));
        tvWelcomeText1.setTypeface(Utility.typeFaceForBoldText(this));
        btnGetStarted.setTypeface(Utility.typeFaceForText(this));
        tvAlreadyAcc.setTypeface(Utility.typeFaceForText(this));
        tvLogin.setTypeface(Utility.typeFaceForText(this));
    }


    public void onLoginClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void onGetStartedClicked(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
