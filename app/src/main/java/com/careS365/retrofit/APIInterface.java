package com.careS365.retrofit;

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
import com.careS365.responseModel.RangeNotifyAllMemberResponseModel;
import com.careS365.responseModel.ResendOtpResponseModel;
import com.careS365.responseModel.ResetPasswordResponseModel;
import com.careS365.responseModel.SaveProfilePicResponseModel;
import com.careS365.responseModel.SignUpResponseModel;
import com.careS365.responseModel.VerifyOTPResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    public static final int SIGNUP_SUCCESS = 1;
    public static final int SIGNUP_FAILED = 2;
    public static final int LOGIN_SUCCESS = 3;
    public static final int LOGIN_FAILED = 4;
    public static final int FORGOT_PASS_SUCCESS = 5;
    public static final int FORGOT_PASS_FAILED = 6;
    public static final int EDIT_USERNAME_SUCCESS = 7;
    public static final int EDIT_USERNAME_FAILED = 8;
    public static final int EDIT_PHONE_SUCCESS = 9;
    public static final int EDIT_PHONE_FAILED = 10;
    public static final int PRIVACY_POLICY_SUCCESS = 11;
    public static final int PRIVACY_POLICY_FAILED = 12;
    public static final int VERIFY_OTP_SUCCESS = 13;
    public static final int VERIFY_OTP_FAILED = 14;
    public static final int FORGOT_PASS_VERIFY_OTP_SUCCESS = 15;
    public static final int FORGOT_PASS_VERIFY_OTP_FAILED = 16;
    public static final int RESET_PASSWORD_SUCCESS = 17;
    public static final int RESET_PASSWORD_FAILED = 18;
    public static final int CREATE_CIRCLE_SUCCESS = 19;
    public static final int CREATE_CIRCLE_FAILED = 20;
    public static final int GET_CIRCLES_SUCCESS = 21;
    public static final int GET_CIRCLES_FAILED = 22;
    public static final int GET_CIRCLES_NO_DATA = 23;
    public static final int EDIT_CIRCLE_NAME_SUCCESS = 24;
    public static final int EDIT_CIRCLE_NAME_FAILED = 25;
    public static final int JOIN_CIRCLE_SUCCESS = 26;
    public static final int JOIN_CIRCLE_FAILED_WRONG_DATA = 27;
    public static final int JOIN_CIRCLE_FAILED = 28;
    public static final int LEAVE_CIRCLE_SUCCESS = 29;
    public static final int LEAVE_CIRCLE_OWNER_FAILED = 35;
    public static final int LEAVE_CIRCLE_FAILED = 30;
    public static final int LOGOUT_SUCCESS = 31;
    public static final int LOGOUT_FAILED = 32;
    public static final int GET_CIRCLE_MEMBERS_SUCCESS = 33;
    public static final int GET_CIRCLE_MEMBERS_FAILED = 34;
    public static final int DELETE_ACCOUNT_SUCCESS = 36;
    public static final int DELETE_ACCOUNT_FAILED = 37;
    public static final int LOGIN_OTP_NOT_VERIFIED = 38;
    public static final int GET_ADMIN_EMAIL_SUCCESS = 39;
    public static final int GET_ADMIN_EMAIL_FAILED = 40;
    public static final int CONTACT_US_SUCCESS = 41;
    public static final int CONTACT_US_FAILED = 42;
    public static final int SAVE_PROFILE_PIC_SUCCESS = 43;
    public static final int SAVE_PROFILE_PIC_FAILED = 44;
    public static final int DELETE_CIRCLE_MEMBERS_SUCCESS = 45;
    public static final int DELETE_CIRCLE_MEMBERS_FAILED = 46;
    public static final int DELETE_CIRCLE_SUCCESS = 47;
    public static final int DELETE_CIRCLE_FAILED = 48;

    public static final int SETTING_NOTIFICATION_SUCCESS = 49;
    public static final int SETTING_NOTIFICATION_FAILEDE = 50;

    public static final int NOTIFICATION_ALL_MEMBERS_SUCCESS = 51;
    public static final int NOTIFICATION__ALL_MEMBERS_FAILEDE = 52;

    public static final int NOTIFIY__ALL_MEMBERS_SUCCESS = 53;
    public static final int NOTIFIY__ALL_MEMBERS_FAILD = 54;

    public static final int RESEND_OTP_SUCCESS = 55;
    public static final int RESEND_OTP_FAILD = 56;


    @Headers({"Accept:application/json"})
    @Multipart
    @POST("user_register.php")
    Call<SignUpResponseModel> signUp(
            @Part("fullname") RequestBody name1,
            @Part("phonenumber") RequestBody phone1,
            @Part("password") RequestBody password1,
            @Part("confirmpassword") RequestBody cnfrmPass1,
            @Part("Image") RequestBody imgReq,
            @Part MultipartBody.Part image);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("user_register.php")
    Call<SignUpResponseModel> signUpWithoutImg(
            @Field("fullname") String username,
            @Field("phonenumber") String phone,
            @Field("password") String password,
            @Field("confirmpassword") String cnfrmPass
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("verify_register_otp.php")
    Call<VerifyOTPResponseModel> verifyOTP(
            @Field("phone_num") String phone,
            @Field("otp") String otp);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("user_login.php")
    Call<LoginResponseModel> login(
            @Field("phonenumber") String phone,
            @Field("password") String password,
            @Field("device_token") String device_token);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ForgotPasswordResponseModel> forgotPass(
            @Field("phone_num") String mobile ,
            @Field("email") String email);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("verify_password_otp.php")
    Call<ForgotPassVerifyOTPResponseModel> forgotPassVerifyOTP(
            @Field("phone_num") String mobile,
            @Field("otp") String otp);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("resetpassword.php")
    Call<ResetPasswordResponseModel> resetPassword(
            @Field("password") String newPass,
            @Field("confirm_password") String cnfrmPass,
            @Field("id") String userId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("create_circle.php")
    Call<CreateCircleResponseModel> createCircle(
            @Field("circle_name") String cicleName,
            @Field("user_id") String userId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("get_user_circles.php")
    Call<GetCirclesResponseModel> getCircles(
            @Field("user_id") String userId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("edit_circle.php")
    Call<EditCircleNameResponseModel> saveCircleName(
            @Field("circle_name") String circleName,
            @Field("id") String circleId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("match_invite_code.php")
    Call<JoinCircleResponseModel> joinCircle(
            @Field("invite_code") String inviteCode,
            @Field("invited_by") String invitedBy,
            @Field("circle_id") String invitedCircle,
            @Field("invited_to") String userId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("leave_circle.php")
    Call<LeaveCircleResponseModel> leaveCircle(
            @Field("user_id") String userId,
            @Field("circle_id") String selectedCircle);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("user_logout.php")
    Call<LogoutResponseModel> logout(
            @Field("user_id") String userId,
            @Field("auth_token") String authToken);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("get_circle_members.php")
    Call<GetCircleMembersResponseModel> getCircleMembers(
            @Field("circle_id") String selectedCircle);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("delete_account.php")
    Call<DeleteAccountResponseModel> deleteAccount(
            @Field("user_id") String userId);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("edit_user_profile.php")
    Call<EditUsernameResponseModel> editUsername(
            @Field("user_id") String userId,
            @Field("full_name") String username);

    @Headers({"Accept: application/json"})
    @POST("get_admin_email.php")
    Call<GetAdminEmailResponseModel> getAdminEmail();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("contact_us.php")
    Call<ContactUsReponseModel> contactUs(
            @Field("email") String email,
            @Field("message") String msg);


    @Multipart
    @POST("update_user_image.php")
    Call<SaveProfilePicResponseModel> saveProfilePic(@Part("user_id") RequestBody user_id,
                                                     @Part MultipartBody.Part image,
                                                     @Part("image") RequestBody imgReq);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("delete_circle_members.php")
    Call<DeleteCircleMembersResponseModel> deleteCircleMembers(
            @Field("circle_id") String selectedCircle,
            @Field("user_id") String userId,
            @Field("delete_ids") String selectedUsers);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("update_phone_num.php")
    Call<EditPhoneResponseModel> editPhone(
            @Field("user_id") String userId,
            @Field("phonenumber") String phone);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("delete_circle.php")
    Call<DeleteCircleResponseModel> deleteCircle(
            @Field("user_id") String userId,
            @Field("circle_id") String selectedCircle);


    //TODO: notification_settings.php
    /*Here notification_settings.php api used for set notification ON or OFF
     * notification_status (1 for ON, 0 for Off)
     * notification_type (1 for range_notification_status, 2 for battery_notification_status)*/

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("notification_settings.php")
    Call<NotificationAlertResponseModel> set_notification(@Field("user_id") String user_id,
                                                          @Field("notification_status") String notification_status,
                                                          @Field("notification_type") String notification_type);

    // TODO : notifiy related range circle members
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("rangeNotify.php")
    Call<RangeNotifyAllMemberResponseModel> range_notification(@Field("user_id") String user_id,
                                                               @Field("circle_id") String circle_id);

    // TODO : notifiy related battery circle members
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("notify.php")
    Call<BatteryLowNotificationResponseModel> battery_notification(@Field("user_id") String user_id,
                                                                   @Field("circle_id") String circle_id,
                                                                   @Field("battery_percentage") String battery_percentage);

    // TODO : notifiy related battery circle members
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("resend_otp.php")
    Call<ResendOtpResponseModel> resendOtp(@Field("phone_num") String phone_num);


}
