package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddCategoryService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.INSERTCATEGORY_URL)
    Call<Category> CheckCategory(
            @Field("cat_name") String cat_name
    );

}
