package com.careS365.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.careS365.R;
import com.careS365.account.presenter.IPAccountActivity;
import com.careS365.account.presenter.PAccountActivity;
import com.careS365.base.BaseClass;
import com.careS365.responseModel.SaveProfilePicResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountActivity extends BaseClass implements IAccountActivity {

    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;

    @BindView(R.id.profilePic)
    CircleImageView profilePic;

    @BindView(R.id.relative_saveUserImage)
    RelativeLayout relative_saveUserImage;

    MultipartBody.Part image;
    RequestBody imgReq;
    Bitmap userImg;

    IPAccountActivity ipAccountActivity;
    ProgressDialog progressDialog;
    Context context;
    String get_User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        context = AccountActivity.this;
        ipAccountActivity = new PAccountActivity(this);
        get_User_ID=Utility.getUserId();
        if (Utility.getUserImage().isEmpty() && Utility.getUserImage() == null) {
            Glide.with(context).load(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(profilePic);
        } else {
            Glide.with(context).load(Constants.IMAGE_URL + Utility.getUserImage()).into(profilePic);
        }
        eventListners();

    }

    private void eventListners() {
        relative_saveUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(context)) {
                    progressDialog = Utility.showLoader(context);
                    ipAccountActivity.saveProfilePic(Utility.getUserId(), image, imgReq);
                } else {
                    Toast.makeText(AccountActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackClicked(View view) {
        //startActivity(new Intent(this, HomeScreenActivity.class));
        super.onBackPressed();
    }

    public void onAddPhotoClicked(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            userImg = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(userImg);
            try {
                image = sendImageFileToserver(userImg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //encodedImg = encodeTobase64(photo);
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                Uri selectedImage = data.getData();
                //ivProfilePic.setImageURI(selectedImage);

                /*String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));*/


                //Uri imageUri = data.getData();
                try {
                    userImg = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    profilePic.setImageBitmap(userImg);
                    image = sendImageFileToserver(userImg);
                    //encodedImg = encodeTobase64(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MultipartBody.Part sendImageFileToserver(Bitmap bitMap) throws IOException {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "user_image" + System.currentTimeMillis() + ".png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        RequestBody reqFile = RequestBody.create(MediaType.parse("user_image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        imgReq = RequestBody.create(MediaType.parse("text/plain"), "image");
        return fileToUpload;
    }

    public void onEditUsernameClicked(View view) {
        Intent intent = new Intent(this, EditUserNameActivity.class);
        startActivity(intent);
    }

    public void onEditPhoneClicked(View view) {
        Intent intent = new Intent(this, EditPhoneNumberActivity.class);
        startActivity(intent);
    }

    public void onChangePasswordClicked(View view) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("get_User_ID",get_User_ID);
        startActivity(intent);
    }

    public void onDeleteAccountClicked(View view) {
        Intent intent = new Intent(this, DeleteAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveProfilePicSuccessFromPresenterToActivity(SaveProfilePicResponseModel saveProfilePicResponseModel) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + saveProfilePicResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveProfilePicFailureFromPresenterToActivity(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
