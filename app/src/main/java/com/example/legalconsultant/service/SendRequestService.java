package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.util.EndPoint;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SendRequestService {

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(EndPoint.SendRequestAPI_URL)
    Call<Request> SendRequest(
            @Part("fk_lawyer_id") RequestBody fk_lawyer_id,
            @Part("fk_client_id") RequestBody fk_client_id,
            @Part MultipartBody.Part pdf
    );

}
