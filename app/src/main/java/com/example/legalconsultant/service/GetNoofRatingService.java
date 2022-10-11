package com.example.legalconsultant.service;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetNoofRatingService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Get_All_rating)
    Call<JsonObject> getallratingno(
            @Field("fk_lawyer_id") int fk_lawyer_id
    );
}
