package com.careS365.retrofit;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.careS365.base.MyApp;
import com.careS365.home.bottomFragments.responseModel.BatteryLowNotificationResponseModel;
import com.careS365.notification.responseModel.NotificationAlertResponseModel;
import com.careS365.responseModel.ContactUsReponseModel;
import com.careS365.responseModel.CreateCircleResponseModel;
import com.careS365.responseModel.DeleteAccountResponseModel;
import com.careS365.responseModel.DeleteCircleMembersResponseModel;
import com.careS365.responseModel.DeleteCircleResponseModel;
import com.careS365.responseModel.EditCircleNameResponseModel;
import com.careS365.responseModel.EditPhoneResponseModel;
import com.careS365.responseModel.EditUsernameResponseModel;
import com.careS365.responseModel.ForgotPassVerifyOTPResponseModel;
import com.careS365.responseModel.ForgotPasswordResponseModel;
import com.careS365.responseModel.GetAdminEmailResponseModel;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.responseModel.GetCirclesResponseModel;
import com.careS365.responseModel.JoinCircleResponseModel;
import com.careS365.responseModel.LeaveCircleResponseModel;
import com.careS365.responseModel.LoginResponseModel;
import com.careS365.responseModel.LogoutResponseModel;
import com.careS365.responseModel.PrivacyPolicyResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;
import com.careS365.responseModel.ResetPasswordResponseModel;
import com.careS365.responseModel.SaveProfilePicResponseModel;
import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;
import com.careS365.util.PreferenceHandler;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCalls {

    private APIInterface apiInterface;

    public RetrofitCalls() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void signUp(String username, String phone, String password, String cnfrmPass, MultipartBody.Part image, RequestBody imgReq, final Handler mHandler) {
        RequestBody username1 = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody phone1 = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody cnfrmPass1 = RequestBody.create(MediaType.parse("text/plain"), cnfrmPass);
        final Message message = new Message();
        Call<SignUpResponseModel> call = apiInterface.signUp(username1, phone1, password1, cnfrmPass1, imgReq, image);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.SIGNUP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.SIGNUP_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                message.what = apiInterface.SIGNUP_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void uploadImg(MultipartBody.Part userImg, RequestBody userImgName, final Handler mHandler) {
       /* final Message message = new Message();
        Call<SignUpResponseModel> call = apiInterface.uploadImg(userImgName,userImg);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
                if (response.body() != null) {
                    Log.e("Status().code","" + response.code());
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.SIGNUP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.SIGNUP_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                Log.e("Status().equals(200)","SUCCESS");
                message.what = apiInterface.SIGNUP_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg","" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });*/
    }

    public void login(String phone, String password, String device_token, final Handler mHandler) {
        final Message message = new Message();
        Call<LoginResponseModel> call = apiInterface.login(phone, password, device_token);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        /*Error for not apply LoginSucess*/
                        //ToDo : Login Success response message
                        message.what = apiInterface.LOGIN_SUCCESS;
                        message.obj = response.body();
                        String id = response.body().getData().getId();
                        String name = response.body().getData().getFullName();
                        String phone_number = response.body().getData().getPhoneNum();
                        String authToken = response.body().getData().getAuthToken();
                        String range_notify_status = response.body().getData().getRangeNotifyStatus();
                        String battery_notify_status = response.body().getData().getBatteryNotifyStatus();
                        String image = response.body().getData().getImage();
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_ID, id);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_AUTH_TOKEN, authToken);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_NAME, name);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_USER_PHONE_NUMBER, phone_number);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_RANGE_NOTIFY_STATUS, range_notify_status);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_BATTERY_NOTIFY_STATUS, battery_notify_status);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_USER_IMAGE, image);
                        String userId = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_ID, "");
                        // Log.d("+++++++++", "++ id read++" + userId);
                        mHandler.sendMessage(message);
                    } else if (response.body().getStatus() == 401) {
                        message.what = apiInterface.LOGIN_OTP_NOT_VERIFIED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.LOGIN_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                message.what = apiInterface.LOGIN_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void forgotPass(String mobile, String email, final Handler mHandler) {
        final Message message = new Message();
        Call<ForgotPasswordResponseModel> call = apiInterface.forgotPass(mobile,email);
        call.enqueue(new Callback<ForgotPasswordResponseModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponseModel> call, Response<ForgotPasswordResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.FORGOT_PASS_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.FORGOT_PASS_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<ForgotPasswordResponseModel> call, Throwable t) {
                message.what = apiInterface.FORGOT_PASS_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void editUsername(String username, String userId, final Handler mHandler) {
        final Message message = new Message();
        Call<EditUsernameResponseModel> call = apiInterface.editUsername(userId, username);
        call.enqueue(new Callback<EditUsernameResponseModel>() {
            @Override
            public void onResponse(Call<EditUsernameResponseModel> call, Response<EditUsernameResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.EDIT_USERNAME_SUCCESS;
                        message.obj = response.body();
                        String name = response.body().getData().getFullName();
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_NAME, name);
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.EDIT_USERNAME_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<EditUsernameResponseModel> call, Throwable t) {
                message.what = apiInterface.EDIT_USERNAME_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void editPhone(String phone, String userId, final Handler mHandler) {
        final Message message = new Message();
        Call<EditPhoneResponseModel> call = apiInterface.editPhone(userId, phone);
        call.enqueue(new Callback<EditPhoneResponseModel>() {
            @Override
            public void onResponse(Call<EditPhoneResponseModel> call, Response<EditPhoneResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.EDIT_PHONE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.EDIT_PHONE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<EditPhoneResponseModel> call, Throwable t) {
                message.what = apiInterface.EDIT_PHONE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });

    }
    //Todo: comment for the time
   /* public void privacyPolicy(String userId, final Handler mHandler) {
        final Message message = new Message();
        Call<PrivacyPolicyResponseModel> call = apiInterface.privacyPolicy(userId);
        call.enqueue(new Callback<PrivacyPolicyResponseModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyResponseModel> call, Response<PrivacyPolicyResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.EDIT_PHONE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.EDIT_PHONE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<PrivacyPolicyResponseModel> call, Throwable t) {
                message.what = apiInterface.EDIT_PHONE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg","" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });

    }*/

    public void signUpWithoutImg(String username, String phone, String password, String cnfrmPass, final Handler mHandler) {
        final Message message = new Message();
        Call<SignUpResponseModel> call = apiInterface.signUpWithoutImg(username, phone, password, cnfrmPass);
        call.enqueue(new Callback<SignUpResponseModel>() {
            @Override
            public void onResponse(Call<SignUpResponseModel> call, Response<SignUpResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.SIGNUP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.SIGNUP_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpResponseModel> call, Throwable t) {
                message.what = apiInterface.SIGNUP_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });

    }

    public void verifyOTP(String phone, String otp, final Handler mHandler) {
        final Message message = new Message();
        Call<VerifyOTPResponseModel> call = apiInterface.verifyOTP(phone, otp);
        call.enqueue(new Callback<VerifyOTPResponseModel>() {
            @Override
            public void onResponse(Call<VerifyOTPResponseModel> call, Response<VerifyOTPResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.VERIFY_OTP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.VERIFY_OTP_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<VerifyOTPResponseModel> call, Throwable t) {
                message.what = apiInterface.VERIFY_OTP_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void forgotPassVerifyOTP(String mobile, String otp, Handler mHandler) {
        final Message message = new Message();
        Call<ForgotPassVerifyOTPResponseModel> call = apiInterface.forgotPassVerifyOTP(mobile, otp);
        call.enqueue(new Callback<ForgotPassVerifyOTPResponseModel>() {
            @Override
            public void onResponse(Call<ForgotPassVerifyOTPResponseModel> call, Response<ForgotPassVerifyOTPResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.FORGOT_PASS_VERIFY_OTP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.FORGOT_PASS_VERIFY_OTP_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<ForgotPassVerifyOTPResponseModel> call, Throwable t) {
                message.what = apiInterface.FORGOT_PASS_VERIFY_OTP_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void resetPassword(String newPass, String cnfrmPass, String userId, Handler mHandler) {
        final Message message = new Message();
        Call<ResetPasswordResponseModel> call = apiInterface.resetPassword(newPass, cnfrmPass, userId);
        call.enqueue(new Callback<ResetPasswordResponseModel>() {
            @Override
            public void onResponse(Call<ResetPasswordResponseModel> call, Response<ResetPasswordResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.RESET_PASSWORD_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.RESET_PASSWORD_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResetPasswordResponseModel> call, Throwable t) {
                message.what = apiInterface.RESET_PASSWORD_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void createCircle(String cicleName, String userId, Handler mHandler) {
        final Message message = new Message();
        Call<CreateCircleResponseModel> call = apiInterface.createCircle(cicleName, userId);
        call.enqueue(new Callback<CreateCircleResponseModel>() {
            @Override
            public void onResponse(Call<CreateCircleResponseModel> call, Response<CreateCircleResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.CREATE_CIRCLE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.CREATE_CIRCLE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<CreateCircleResponseModel> call, Throwable t) {
                message.what = apiInterface.CREATE_CIRCLE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void getCircles(String userId, Handler mHandler) {
        final Message message = new Message();
        Call<GetCirclesResponseModel> call = apiInterface.getCircles(userId);
        call.enqueue(new Callback<GetCirclesResponseModel>() {
            @Override
            public void onResponse(Call<GetCirclesResponseModel> call, Response<GetCirclesResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.GET_CIRCLES_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else if (response.body().getStatus() == 400) {
                        message.what = apiInterface.GET_CIRCLES_NO_DATA;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.GET_CIRCLES_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCirclesResponseModel> call, Throwable t) {
                message.what = apiInterface.GET_CIRCLES_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void saveCircleName(String circleName, String circleId, Handler mHandler) {
        final Message message = new Message();
        Call<EditCircleNameResponseModel> call = apiInterface.saveCircleName(circleName, circleId);
        call.enqueue(new Callback<EditCircleNameResponseModel>() {
            @Override
            public void onResponse(Call<EditCircleNameResponseModel> call, Response<EditCircleNameResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.EDIT_CIRCLE_NAME_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.EDIT_CIRCLE_NAME_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<EditCircleNameResponseModel> call, Throwable t) {
                message.what = apiInterface.EDIT_CIRCLE_NAME_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void joinCircle(String inviteCode, String invitedBy, String invitedCircle, String userId, final Handler mHandler) {
        final Message message = new Message();
        Call<JoinCircleResponseModel> call = apiInterface.joinCircle(inviteCode, invitedBy, invitedCircle, userId);
        call.enqueue(new Callback<JoinCircleResponseModel>() {
            @Override
            public void onResponse(Call<JoinCircleResponseModel> call, Response<JoinCircleResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.JOIN_CIRCLE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else if (response.body().getStatus() == 401) {
                        message.what = apiInterface.JOIN_CIRCLE_FAILED_WRONG_DATA;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.JOIN_CIRCLE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinCircleResponseModel> call, Throwable t) {
                message.what = apiInterface.JOIN_CIRCLE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void leaveCircle(String userId, String selectedCircle, Handler mHandler) {
        final Message message = new Message();
        Call<LeaveCircleResponseModel> call = apiInterface.leaveCircle(userId, selectedCircle);
        call.enqueue(new Callback<LeaveCircleResponseModel>() {
            @Override
            public void onResponse(Call<LeaveCircleResponseModel> call, Response<LeaveCircleResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.LEAVE_CIRCLE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else if (response.body().getStatus() == 401) {
                        message.what = apiInterface.LEAVE_CIRCLE_OWNER_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.LEAVE_CIRCLE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaveCircleResponseModel> call, Throwable t) {
                message.what = apiInterface.LEAVE_CIRCLE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void logout(String userId, String authToken, final Handler mHandler) {
        final Message message = new Message();
        Call<LogoutResponseModel> call = apiInterface.logout(userId, authToken);
        call.enqueue(new Callback<LogoutResponseModel>() {
            @Override
            public void onResponse(Call<LogoutResponseModel> call, Response<LogoutResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.LOGOUT_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.LOGOUT_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutResponseModel> call, Throwable t) {
                message.what = apiInterface.LOGOUT_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void getCircleMembers(String selectedCircle, Handler mHandler) {
        final Message message = new Message();
        Call<GetCircleMembersResponseModel> call = apiInterface.getCircleMembers(selectedCircle);
        call.enqueue(new Callback<GetCircleMembersResponseModel>() {
            @Override
            public void onResponse(Call<GetCircleMembersResponseModel> call, Response<GetCircleMembersResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.GET_CIRCLE_MEMBERS_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.GET_CIRCLE_MEMBERS_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCircleMembersResponseModel> call, Throwable t) {
                message.what = apiInterface.GET_CIRCLE_MEMBERS_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void deleteAccount(String userId, final Handler mHandler) {
        final Message message = new Message();
        Call<DeleteAccountResponseModel> call = apiInterface.deleteAccount(userId);
        call.enqueue(new Callback<DeleteAccountResponseModel>() {
            @Override
            public void onResponse(Call<DeleteAccountResponseModel> call, Response<DeleteAccountResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.DELETE_ACCOUNT_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.DELETE_ACCOUNT_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponseModel> call, Throwable t) {
                message.what = apiInterface.DELETE_ACCOUNT_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void getAdminEmail(Handler mHandler) {
        final Message message = new Message();
        Call<GetAdminEmailResponseModel> call = apiInterface.getAdminEmail();
        call.enqueue(new Callback<GetAdminEmailResponseModel>() {
            @Override
            public void onResponse(Call<GetAdminEmailResponseModel> call, Response<GetAdminEmailResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.GET_ADMIN_EMAIL_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.GET_ADMIN_EMAIL_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAdminEmailResponseModel> call, Throwable t) {
                message.what = apiInterface.GET_ADMIN_EMAIL_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void contactUs(String email, String msg, Handler mHandler) {
        final Message message = new Message();
        Call<ContactUsReponseModel> call = apiInterface.contactUs(email, msg);
        call.enqueue(new Callback<ContactUsReponseModel>() {
            @Override
            public void onResponse(Call<ContactUsReponseModel> call, Response<ContactUsReponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.CONTACT_US_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.CONTACT_US_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactUsReponseModel> call, Throwable t) {
                message.what = apiInterface.CONTACT_US_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    //TODO: saveProfilePic api method to upload user profile image to server
    public void saveProfilePic(String userId, MultipartBody.Part image, RequestBody imgReq, final Handler mHandler) {


        RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), userId);

        final Message message = new Message();

        Call<SaveProfilePicResponseModel> call = apiInterface.saveProfilePic(user_Id, image, imgReq);

        call.enqueue(new Callback<SaveProfilePicResponseModel>() {
            @Override
            public void onResponse(Call<SaveProfilePicResponseModel> call, Response<SaveProfilePicResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.SAVE_PROFILE_PIC_SUCCESS;
                        message.obj = response.body();
                        String image = response.body().getData().getImage();
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_USER_IMAGE, image);
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.SAVE_PROFILE_PIC_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveProfilePicResponseModel> call, Throwable t) {
                message.what = apiInterface.SAVE_PROFILE_PIC_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void deleteCircleMembers(String selectedCircle, String userId, String selectedUsers, Handler mHandler) {
        final Message message = new Message();
        Call<DeleteCircleMembersResponseModel> call = apiInterface.deleteCircleMembers(selectedCircle, userId, selectedUsers);
        call.enqueue(new Callback<DeleteCircleMembersResponseModel>() {
            @Override
            public void onResponse(Call<DeleteCircleMembersResponseModel> call, Response<DeleteCircleMembersResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.DELETE_CIRCLE_MEMBERS_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.DELETE_CIRCLE_MEMBERS_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCircleMembersResponseModel> call, Throwable t) {
                message.what = apiInterface.DELETE_CIRCLE_MEMBERS_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    public void deleteCircle(String userId, String selectedCircle, Handler mHandler) {
        final Message message = new Message();
        Call<DeleteCircleResponseModel> call = apiInterface.deleteCircle(userId, selectedCircle);
        call.enqueue(new Callback<DeleteCircleResponseModel>() {
            @Override
            public void onResponse(Call<DeleteCircleResponseModel> call, Response<DeleteCircleResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.DELETE_CIRCLE_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.DELETE_CIRCLE_FAILED;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCircleResponseModel> call, Throwable t) {
                message.what = apiInterface.DELETE_CIRCLE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }

    /*public void loginUser(String email, String password,String device_token, final Handler mHandler) {
        final Message message = new Message();
        Call<LoginResponseModel> call = apiInterface.loginUser(email,password,device_token);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    if (response.body() != null) {
                        Log.e("Status().code","" + response.code());
                        if (response.body().getStatus().equalsIgnoreCase("200")) {
                            message.what = apiInterface.LOGIN_SUCCESS;
                            message.obj = response.body();
                            String token = response.body().getData().get(0).getRememberToken();
                            int id = response.body().getData().get(0).getId();
                            Log.d("+++++++++", "++ access token++" + token);
                            Log.d("+++++++++", "++ id++" + id);
                            new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_TOKEN, token);
                            new PreferenceHandler().writeInteger(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_ID, id);
                            String mLoginToken = new PreferenceHandler().readString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_TOKEN, "");
                            int userId = new PreferenceHandler().readInteger(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_LOGIN_USER_ID, 0);
                            Log.d("+++++++++", "++ access token read++" + mLoginToken);
                            Log.d("+++++++++", "++ id read++" + userId);
                            mHandler.sendMessage(message);
                        } else if(response.body().getStatus().equalsIgnoreCase("401")){
                            message.what = apiInterface.OTP_NOT_VERIFIED;
                            message.obj = response.body();
                            mHandler.sendMessage(message);
                        }else {
                            message.what = apiInterface.LOGIN_FAILED;
                            message.obj = response.body().getMessage();
                            mHandler.sendMessage(message);
                        }
                    }

                }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.e("Status().equals(200)","SUCCESS");
                message.what = apiInterface.LOGIN_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg","" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }*/


    //TODO: set_notification_API
    /*Here set_notification_API api used for set notification ON or OFF
     * notification_status (1 for ON, 0 for Off)
     * notification_type (1 for range_notification_status, 2 for battery_notification_status)*/

    public void set_notification_API(String user_id, String notification_status, String notification_type, Handler mHandler) {
        final Message message = new Message();
        Call<NotificationAlertResponseModel> call = apiInterface.set_notification(user_id, notification_status, notification_type);
        call.enqueue(new Callback<NotificationAlertResponseModel>() {
            @Override
            public void onResponse(Call<NotificationAlertResponseModel> call, Response<NotificationAlertResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.SETTING_NOTIFICATION_SUCCESS;
                        message.obj = response.body();
                        String range_notify_status = response.body().getData().getRangeNotifyStatus();
                        String battery_notify_status = response.body().getData().getBatteryNotifyStatus();
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_RANGE_NOTIFY_STATUS, range_notify_status);
                        new PreferenceHandler().writeString(MyApp.getInstance().getApplicationContext(), PreferenceHandler.PREF_KEY_BATTERY_NOTIFY_STATUS, battery_notify_status);
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.SETTING_NOTIFICATION_FAILEDE;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationAlertResponseModel> call, Throwable t) {
                message.what = apiInterface.SETTING_NOTIFICATION_FAILEDE;
                message.obj = t.getMessage();
                mHandler.sendMessage(message);
            }
        });
    }

    //TODO: notify all member for battery low
    public void notifyAllMembers(String user_id, String circle_id, String battery_percentage, Handler mHandler) {
        final Message message = new Message();
        Call<BatteryLowNotificationResponseModel> call = apiInterface.battery_notification(user_id, circle_id, battery_percentage);
        call.enqueue(new Callback<BatteryLowNotificationResponseModel>() {
            @Override
            public void onResponse(Call<BatteryLowNotificationResponseModel> call, Response<BatteryLowNotificationResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.NOTIFIY__ALL_MEMBERS_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.NOTIFIY__ALL_MEMBERS_FAILD;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<BatteryLowNotificationResponseModel> call, Throwable t) {
                message.what = apiInterface.NOTIFIY__ALL_MEMBERS_FAILD;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });
    }


    public void resendApi(String phone_num, final Handler mHandler) {
        final Message message = new Message();
        Call<ResendOtpResponseModel> call = apiInterface.resendOtp(phone_num);
        call.enqueue(new Callback<ResendOtpResponseModel>() {
            @Override
            public void onResponse(Call<ResendOtpResponseModel> call, Response<ResendOtpResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        message.what = apiInterface.RESEND_OTP_SUCCESS;
                        message.obj = response.body();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = apiInterface.RESEND_OTP_FAILD;
                        message.obj = response.body().getMessage();
                        mHandler.sendMessage(message);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResendOtpResponseModel> call, Throwable t) {
                message.what = apiInterface.EDIT_PHONE_FAILED;
                message.obj = t.getMessage();
                Log.e("Error msg", "" + t.getMessage());
                mHandler.sendMessage(message);
            }
        });

    }

}
