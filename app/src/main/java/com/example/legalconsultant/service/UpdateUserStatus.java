package com.example.legalconsultant.service;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateUserStatus {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.UPDATE_USER_STATUS)
    Call<User> UpdateStatus(
            @Field("user_status") String user_status,
            @Field("user_id") int user_id);
}
