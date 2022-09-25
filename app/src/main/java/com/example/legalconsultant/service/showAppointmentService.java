package com.example.legalconsultant.service;

import android.widget.ListAdapter;

import com.example.legalconsultant.util.EndPoint;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface showAppointmentService extends ListAdapter {

    @Headers({"Accept: application/jason"})
    @FormUrlEncoded
    @POST(EndPoint.showAppointment_URL)
    Call<JsonObject>showtimetable(
            @Field("ttt_day") String ttt_day,
            @Field("ttt_fk_lawyer_id") int ttt_fk_lawyer_id
    );
}
