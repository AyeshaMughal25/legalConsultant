package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateCourtDetail {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Update_Court_URL)
    Call<Court> UpdateCourtDetail(
            @Field("court_id") int court_id,
            @Field("court_name") String court_name,
            @Field("court_city") String court_city,
            @Field("court_status") String court_status);
}

