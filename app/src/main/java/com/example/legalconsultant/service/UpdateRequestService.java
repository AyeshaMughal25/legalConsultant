package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateRequestService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Update_REQUEST_URL)
    Call<Request>UpdateRequestStatus(
            @Field("status") String status,
            @Field("req_id") int req_id);

}
