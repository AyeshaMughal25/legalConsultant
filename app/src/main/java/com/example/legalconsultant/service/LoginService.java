package com.example.legalconsultant.service;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.LOGIN_URL)
    Call<User> loginUser(
            @Field("user_email") String user_email,
            @Field("user_password") String user_password
    );

}
