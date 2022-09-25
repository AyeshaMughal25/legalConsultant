package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InsertCourtService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.ADD_COURT_URL)
    Call<Court> AddCourt(
            @Field("court_name") String court_name,
            @Field("court_city") String court_city
    );


}
