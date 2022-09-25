package com.example.legalconsultant.service;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetLawyerByCat extends ListAdapter {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Get_LawyerBy_Cat)
    Call<JsonObject> getlawyercategory(
            @Field("user_category_id") int user_category_id
    );

}


