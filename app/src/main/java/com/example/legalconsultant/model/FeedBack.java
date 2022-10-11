package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("fk_request_id")
    @Expose
    private int fk_request_id;
    @SerializedName("fk_client_id")
    @Expose
    private int fk_client_id;
    @SerializedName("fk_lawyer_id")
    @Expose
    private int fk_lawyer_id;
    @SerializedName("case_subject")
    @Expose
    private String case_subject;
    @SerializedName("feedback")
    @Expose
    private String feedback;
     @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public FeedBack() {

    }

    public FeedBack(int id,String case_subject, String feedback, int rating) {
        this.id = id;
        this.case_subject = case_subject;
        this.feedback = feedback;
        this.rating = rating;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_request_id() {
        return fk_request_id;
    }

    public void setFk_request_id(int fk_request_id) {
        this.fk_request_id = fk_request_id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFk_client_id() {
        return fk_client_id;
    }

    public void setFk_client_id(int fk_client_id) {
        this.fk_client_id = fk_client_id;
    }

    public int getFk_lawyer_id() {
        return fk_lawyer_id;
    }

    public void setFk_lawyer_id(int fk_lawyer_id) {
        this.fk_lawyer_id = fk_lawyer_id;
    }

    public String getCase_subject() {
        return case_subject;
    }

    public void setCase_subject(String case_subject) {
        this.case_subject = case_subject;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public FeedBack(int id, int fk_request_id,int fk_client_id, int fk_lawyer_id, String case_subject, String feedback, int rating) {

        this.id = id;
        this.fk_request_id = fk_request_id;
        this.fk_client_id = fk_client_id;
        this.fk_lawyer_id = fk_lawyer_id;
        this.case_subject = case_subject;
        this.feedback = feedback;
        this.rating = rating;
    }
}
