package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeleteCategory {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.DELETE_CATEGORY_URL)
    Call<Category> DeleteCategory(
            @Field("cat_id") int cat_id
    );
}
