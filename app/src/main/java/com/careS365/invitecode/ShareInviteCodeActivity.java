package com.careS365.invitecode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.home.HomeActivity;
import com.careS365.util.Constants;
import com.careS365.util.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareInviteCodeActivity extends BaseClass {

    @BindView(R.id.et_circle_name1)
    TextView tvCircleName1;
    @BindView(R.id.et_circle_name2)
    TextView tvCircleName2;
    String mInvitationUrl;
    String inviteCode;

    public static final int SHARE_INVITE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_invite_code);
        ButterKnife.bind(this);

        for (int i = 0; i < Constants.getCirclesResponseModel.getData().size(); i++) {
            if (Constants.SELECTED_CIRCLE.equals(Constants.getCirclesResponseModel.getData().get(i).getId())) {
                tvCircleName1.setText(Constants.getCirclesResponseModel.getData().get(i).getInviteCode().substring(0, 3));
                tvCircleName2.setText(Constants.getCirclesResponseModel.getData().get(i).getInviteCode().substring(3, 6));
            }
        }

        inviteCode = tvCircleName1.getText().toString() + tvCircleName2.getText().toString();
        Log.e("invite code:", "" + inviteCode);
        createDynamicLink();
    }

    private void createDynamicLink() {

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://care365.page.link/?invitedCode=" + inviteCode + "&userId=" + Utility.getUserId() + "&circleId=" + Constants.SELECTED_CIRCLE))
                .setDomainUriPrefix("care365.page.link")
                // Open links with this app on Android  amitpandey12.page.link
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("care365.page.link").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse("https://" + dynamicLink.getUri().toString()))
                .buildShortDynamicLink()
                .addOnCompleteListener(ShareInviteCodeActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            mInvitationUrl = shortLink.toString();

                        } else {

                        }
                    }
                });
    }

    public void onBackClicked(View view) {
        super.onBackPressed();
    }

    public void onSendCodeClicked(View view) {

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        //share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");
        share.putExtra(Intent.EXTRA_TEXT, mInvitationUrl);
        startActivityForResult(Intent.createChooser(share, "Share link!"), SHARE_INVITE_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARE_INVITE_CODE && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
