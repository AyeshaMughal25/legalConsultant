package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Court {

    @SerializedName("court_id")
    @Expose
    private int court_id;

    @SerializedName("court_name")
    @Expose
    private String court_name;

    @SerializedName("court_city")
    @Expose
    private String court_city;

    @SerializedName("court_status")
    @Expose
    private String court_status;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public Court() {
    }

    public Court(int court_id, String court_name, String court_city, String court_status) {
        this.court_id = court_id;
        this.court_name = court_name;
        this.court_city = court_city;
        this.court_status = court_status;
    }

    public int getCourt_id() {
        return court_id;
    }

    public void setCourt_id(int court_id) {
        this.court_id = court_id;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public String getCourt_city() {
        return court_city;
    }

    public void setCourt_city(String court_city) {
        this.court_city = court_city;
    }

    public String getCourt_status() {
        return court_status;
    }

    public void setCourt_status(String court_status) {
        this.court_status = court_status;
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
}
