package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {
    @SerializedName("req_id")
    @Expose
    private int req_id;
    @SerializedName("fk_lawyer_id")
    @Expose
    private int fk_lawyer_id;
    @SerializedName("fk_client_id")
    @Expose
    private int fk_client_id;
    @SerializedName("pdf")
    @Expose
    private String pdf;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_time")
    @Expose
    private String date_time;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("user_cnic")
    @Expose
    private String user_cnic;

    @SerializedName("user_contact")
    @Expose
    private String user_contact;

    @SerializedName("user_image")
    @Expose
    private String user_image;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public Request(String user_name, String user_cnic, String user_contact, String user_image,
                   int req_id, int fk_lawyer_id, int fk_client_id,
                   String pdf, String status, String date_time) {
        this.user_name = user_name;
        this.user_cnic = user_cnic;
        this.user_contact = user_contact;
        this.user_image = user_image;
        this.req_id = req_id;
        this.fk_lawyer_id = fk_lawyer_id;
        this.fk_client_id = fk_client_id;
        this.pdf = pdf;
        this.status = status;
        this.date_time = date_time;
    }

    public Request(String user_name, String user_cnic, String user_contact, String user_image,
                   int req_id, int fk_client_id,
                   String pdf, String status, String date_time) {
        this.user_name = user_name;
        this.user_cnic = user_cnic;
        this.user_contact = user_contact;
        this.user_image = user_image;
        this.req_id = req_id;
        this.fk_client_id = fk_client_id;
        this.pdf = pdf;
        this.status = status;
        this.date_time = date_time;
    }

    public Request() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_cnic() {
        return user_cnic;
    }

    public void setUser_cnic(String user_cnic) {
        this.user_cnic = user_cnic;
    }

    public String getUser_contact() {
        return user_contact;
    }

    public void setUser_contact(String user_contact) {
        this.user_contact = user_contact;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public int getFk_lawyer_id() {
        return fk_lawyer_id;
    }

    public void setFk_lawyer_id(int fk_lawyer_id) {
        this.fk_lawyer_id = fk_lawyer_id;
    }

    public int getFk_client_id() {
        return fk_client_id;
    }

    public void setFk_client_id(int fk_client_id) {
        this.fk_client_id = fk_client_id;
    }


    public String getPdf() {
        return pdf;
    }

    public void setPdf(String user_pdf) {
        this.pdf = pdf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String user_status) {
        this.status = status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
