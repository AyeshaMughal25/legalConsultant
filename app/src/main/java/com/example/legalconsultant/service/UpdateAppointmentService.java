package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateAppointmentService {
    @Headers({"Accept: application/json" })
    @FormUrlEncoded
    @POST(EndPoint.UpdateAppointment_URL)
    Call<Appointment> UpdateAppointment(
            @Field("appt_id") int appt_id,
            @Field("appt_status") String appt_status,
            @Field("appt_reason") String appt_reason);
}
