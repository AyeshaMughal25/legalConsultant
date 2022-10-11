package com.example.legalconsultant.service;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.FeedBack;
import com.example.legalconsultant.util.EndPoint;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FeedBackFromUserService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(EndPoint.Feedback_From_User_URL)
    Call<FeedBack> feedbackfromuser(
            @Field("fk_request_id") int fk_request_id,
            @Field("fk_client_id") int fk_client_id,
            @Field("fk_lawyer_id") int fk_lawyer_id,
            @Field("case_subject") String case_subject,
            @Field("feedback") String feedback,
            @Field("rating") int rating
    );

}
