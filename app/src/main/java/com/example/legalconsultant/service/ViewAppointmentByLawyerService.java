package com.example.legalconsultant.service;

import android.widget.ListAdapter;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface ViewAppointmentByLawyerService extends ListAdapter {
    @Headers({"Accept: application/jason"})
    @FormUrlEncoded
    @POST(EndPoint.ViewAppointmentbyLawyer_URL)
    Call<JsonObject> getallappoinment(
            @Field("fk_lawyer_id") int fk_lawyer_id
    );

}
