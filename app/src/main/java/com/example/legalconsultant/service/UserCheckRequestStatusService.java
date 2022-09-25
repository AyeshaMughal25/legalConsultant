package com.example.legalconsultant.service;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserCheckRequestStatusService extends ListAdapter {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.UserCheckRequestStatus_URL_)
    Call<JsonObject> Check_Status(
            @Field("fk_client_id") int fk_client_id
    );

}
