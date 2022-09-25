package com.example.legalconsultant.service;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserRegistrationService {

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(EndPoint.USER_REGISTRATION_STATUS)
    Call<User> UserRegister(
            @Part("user_name") RequestBody user_name,
            @Part("user_email") RequestBody user_email,
            @Part("user_password") RequestBody user_password,
            @Part("user_cnic") RequestBody user_cnic,
            @Part("user_contact") RequestBody user_contact,
            @Part("user_type") RequestBody user_type,
            @Part("user_status") RequestBody user_status,
            @Part MultipartBody.Part user_image
    );

}
