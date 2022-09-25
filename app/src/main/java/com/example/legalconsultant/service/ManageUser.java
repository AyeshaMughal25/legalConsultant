package com.example.legalconsultant.service;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ManageUser {

    @Headers({"Accept: application/json"})
    @GET(EndPoint.MANAGE_USER)
    Call<JsonObject> getusers();
}