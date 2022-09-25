package com.example.legalconsultant.service;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetCategoryService {

    @Headers({"Accept: application/json"})
    @GET(EndPoint.GET_CATEGORY_URL)
    Call<JsonObject> getCategories();
}
