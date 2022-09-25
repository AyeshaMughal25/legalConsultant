package com.example.legalconsultant.service;

import android.widget.ListAdapter;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LawyerCheckRequestStatusService extends ListAdapter {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.UserCheckRequestStatus_URL_)
    Call<JsonObject> Check_Status(
            @Field("fk_client_id") int fk_client_id
    );

}
