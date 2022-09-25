package com.example.legalconsultant.service;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateUserDataService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.UpdateUserData_URL)
    Call<User> UpdateUserData(
            @Field("user_name") String user_name,
            @Field("user_email") String user_email,
            @Field("user_contact") String user_contact,
            @Field("user_cnic") String user_cnic,
            @Field("user_id") int user_id);
}
