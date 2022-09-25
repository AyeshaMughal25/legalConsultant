package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MakeAppointmentSevice {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Make_Appointment_URL)
    Call<Appointment> makeappointment(
            @Field("appt_title") String appt_title,
            @Field("appt_description") String appt_description,
            @Field("fk_lawyer_id") int fk_lawyer_id,
            @Field("fk_customer_id") int fk_customer_id,
            @Field("fk_request_id") int fk_request_id,
            @Field("appt_start_time") String appt_start_time,
            @Field("appt_end_time") String appt_end_time,
            @Field("appt_day") String appt_day,
            @Field("appt_date") String appt_date
    );
}
