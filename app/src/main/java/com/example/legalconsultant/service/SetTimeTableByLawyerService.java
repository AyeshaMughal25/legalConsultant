package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.model.Timetable;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SetTimeTableByLawyerService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.SetTimeTableByLawyer_URL)
    Call<Timetable>settimetable(
            @Field("ttt_day") String ttt_day,
            @Field("ttt_start_time") String ttt_start_time,
            @Field("ttt_end_time") String ttt_end_time,
            @Field("ttt_fk_lawyer_id") int ttt_fk_lawyer_id

    );
}
