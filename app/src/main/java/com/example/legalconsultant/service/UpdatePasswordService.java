package com.example.legalconsultant.service;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdatePasswordService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.UpdatePassword_URL)
    Call<User> UpdatePassword(
            @Field("user_password") String user_password,
            @Field("user_id") int user_id);
}
