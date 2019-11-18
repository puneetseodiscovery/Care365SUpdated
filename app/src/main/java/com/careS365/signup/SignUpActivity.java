package com.careS365.signup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careS365.R;
import com.careS365.base.BaseClass;
import com.careS365.login.LoginActivity;
import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;
import com.careS365.util.Utility;
import com.chaos.view.PinView;
import com.firebase.client.Firebase;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignUpActivity extends BaseClass implements ISignUpActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_add_photo)
    TextView tvAddPhotoText;
    @BindView(R.id.et_signup_username)
    EditText etUsername;
    @BindView(R.id.et_signup_phone)
    EditText etPhone;
    @BindView(R.id.et_signup_password)
    EditText etPassword;
    @BindView(R.id.et_signup_cfrm_pass)
    EditText etCnfrmPass;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.tv_already_acc)
    TextView tvAlreadyAcc;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.profilePic)
    CircleImageView cvProfilePic;
    IPSignUpActivity ipSignUpActivity;
    ProgressDialog progressDialog;
    MultipartBody.Part userImg;
    RequestBody userImgName;

    SmsVerifyCatcher smsVerifyCatcher;

    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        ipSignUpActivity = new PSignUpActivity(this);
        init();
    }

    private void init() {
        tvTitle.setTypeface(Utility.typeFaceForText(this));
        tvAddPhotoText.setTypeface(Utility.typeFaceForText(this));
        etUsername.setTypeface(Utility.typeFaceForText(this));
        etPhone.setTypeface(Utility.typeFaceForText(this));
        etPassword.setTypeface(Utility.typeFaceForText(this));
        etCnfrmPass.setTypeface(Utility.typeFaceForText(this));
        btnSignUp.setTypeface(Utility.typeFaceForBoldText(this));
        tvAlreadyAcc.setTypeface(Utility.typeFaceForText(this));
        tvLogin.setTypeface(Utility.typeFaceForText(this));
    }

    private String getOTPfromMessage(String message) {
        Pattern pattern = Pattern.compile("(\\d{4})");
        Matcher matcher = pattern.matcher(message);

        String val = "";
        if (matcher.find()) {
            val = matcher.group(0);  // 4 digit number
        }
        Log.e("OTP:", val);
        return val;
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onCameraClicked(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cvProfilePic.setImageBitmap(photo);
            try {
                userImg = sendImageFileToserver(photo, "0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                Uri selectedImage = data.getData();
                cvProfilePic.setImageURI(selectedImage);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    userImg = sendImageFileToserver(bitmap, "0");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public MultipartBody.Part sendImageFileToserver(Bitmap bitMap, String signature) throws IOException {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "image" + System.currentTimeMillis() + ".png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        userImgName = RequestBody.create(MediaType.parse("text/plain"), "image");

        return body;
    }


    public void onSignUpButtonClicked(View view) {
        if (Utility.isNetworkConnected(this)) {
            if (etUsername.getText().toString().length() > 0 && etPhone.getText().toString().length() > 0 && etPassword.getText().toString().length() > 0
                    && etCnfrmPass.getText().toString().length() > 0) {
                if (etPassword.getText().toString().equalsIgnoreCase(etCnfrmPass.getText().toString())) {
                    if (userImg != null) {
                        progressDialog = Utility.showLoader(this);
                        ipSignUpActivity.signUp(etUsername.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString(), etCnfrmPass.getText().toString(), userImg, userImgName);
                    } else {
                        showImageDialog();
                    }
                } else {
                    etPassword.setError("Password and Confirm Passwor should be same.");
                    etCnfrmPass.setError("Password and Confirm Passwor should be same.");
                    etPassword.requestFocus();
                }
            } else {
                if (etCnfrmPass.getText().toString().length() == 0) {
                    etCnfrmPass.setError("Please fill Confirm Password");
                    etCnfrmPass.requestFocus();
                }

                if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Please fill Password");
                    etPassword.requestFocus();
                }

                if (etPhone.getText().toString().length() == 0) {
                    etPhone.setError("Please enter phone number");
                    etPhone.requestFocus();
                }

                if (etUsername.getText().toString().length() == 0) {
                    etUsername.setError("Please enter username");
                    etUsername.requestFocus();
                }

                if (userImg == null) {
                    showImageDialog();
                }
            }
        } else {
            Toast.makeText(this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void showImageDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_add_pic_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialogView.findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //your business logic
                deleteDialog.dismiss();


                    if (Utility.isNetworkConnected(SignUpActivity.this)) {
                        progressDialog = Utility.showLoader(SignUpActivity.this);
                        ipSignUpActivity.signUpWithoutImg(etUsername.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString(), etCnfrmPass.getText().toString());
                    } else {
                        Toast.makeText(SignUpActivity.this, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

            }
        });
        deleteDialogView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }


    @Override
    public void onSignUpSuccessFromPresenterToActivity(SignUpResponseModel signUpResponseModel) {
        progressDialog.dismiss();
        String userId = signUpResponseModel.getData().getId().toString().trim();
        String password = signUpResponseModel.getData().getId().toString().trim() + signUpResponseModel.getData().getFullname().toString().trim();
        registerUserToFirebase(userId, password);

        Intent refresh = new Intent(this, LoginActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(refresh);
        //showOTPDialog();

        //startActivity(new Intent(this,LoginActivity.class));
    }

    private void registerUserToFirebase(final String userId, final String password) {
        /*final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();*/

        String url = "https://care365-29dbd.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Firebase reference = new Firebase("https://care365-29dbd.firebaseio.com/users");

                if (s.equals("null")) {
                    reference.child(userId).child("password").setValue(password);
                    Log.e("firebase", "registration successful");
                    //Toast.makeText(SignUpActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(s);

                        if (!obj.has(userId)) {
                            reference.child(userId).child("password").setValue(password);
                            //Toast.makeText(SignUpActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                            Log.e("firebase regsiter", "registration successful");
                        } else {
                            Log.e("firebase regsiter", "username already exists");
                            //Toast.makeText(SignUpActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //pd.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
                //pd.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(SignUpActivity.this);
        rQueue.add(request);
    }

    private void showOTPDialog() {
        Log.e("phone:", etPhone.getText().toString());
        LayoutInflater factory = LayoutInflater.from(SignUpActivity.this);
        final View otpDialogView = factory.inflate(R.layout.custom_otp_dialog, null);
        final AlertDialog otpDialog = new AlertDialog.Builder(this).create();
        otpDialog.setView(otpDialogView);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final PinView pinView = otpDialogView.findViewById(R.id.pv_otp);

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                //pinView.setText(getOTPfromMessage(message));
                Log.e("phone1:", etPhone.getText().toString());
                Log.e("otp1:", pinView.getText().toString());
            }
        });

        otpDialogView.findViewById(R.id.btn_submit_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("phone3:", etPhone.getText().toString());
                Log.e("otp3:", pinView.getText().toString());
                progressDialog = Utility.showLoader(SignUpActivity.this);
                ipSignUpActivity.verifyOTP(etPhone.getText().toString(), pinView.getText().toString());
            }
        });
        otpDialog.show();
    }

    @Override
    public void onSignUpFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void verifyOTPSuccessFromPresenterToActivity(VerifyOTPResponseModel verifyOTPResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "Your number is successfully verified.", Toast.LENGTH_SHORT).show();
        Intent refresh = new Intent(this, LoginActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(refresh);
    }

    @Override
    public void verifyOTPFailureFromPresenterToActivity(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + msgg, Toast.LENGTH_SHORT).show();
    }
}
