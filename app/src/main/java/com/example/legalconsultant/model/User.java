package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("user_email")
    @Expose
    private String user_email;

    @SerializedName("user_password")
    @Expose
    private String user_password;

    @SerializedName("user_cnic")
    @Expose
    private String user_cnic;

    @SerializedName("user_contact")
    @Expose
    private String user_contact;

    @SerializedName("user_type")
    @Expose
    private String user_type;

    @SerializedName("user_category_id")
    @Expose
    private int user_category_id;

    @SerializedName("user_pdf")
    @Expose
    private String user_pdf;

    @SerializedName("user_status")
    @Expose
    private String user_status;

    @SerializedName("user_image")
    @Expose
    private String user_image;

    @SerializedName("user_dateTime")
    @Expose
    private String user_dateTime;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("court_name")
    @Expose
    private String court_name;

    @SerializedName("user_city")
    @Expose
    private String user_city;

    @SerializedName("user_fees")
    @Expose
    private int user_fees;
    @SerializedName("lawyer_rating")
    @Expose
    private int lawyer_rating;

    public User() {
    }

    public User(int user_id, String user_name, String user_email, String user_cnic,
                String user_contact, String user_status, String user_image,
                String court_name, String user_city, int user_fees,int lawyer_rating) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_cnic = user_cnic;
        this.user_contact = user_contact;
        this.user_status = user_status;
        this.user_image = user_image;
        this.court_name = court_name;
        this.user_city = user_city;
        this.user_fees = user_fees;
        this.lawyer_rating = lawyer_rating;
    }

    public User(int user_id, String user_name, String user_email, String user_cnic,
                String user_contact, String user_type, String user_pdf, String user_status, String user_image) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_cnic = user_cnic;
        this.user_contact = user_contact;
        this.user_type = user_type;
        this.user_pdf = user_pdf;
        this.user_status = user_status;
        this.user_image = user_image;
    }

    public int getLawyer_rating() {
        return lawyer_rating;
    }

    public void setLawyer_rating(int lawyer_rating) {
        this.lawyer_rating = lawyer_rating;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getUser_category_id() {
        return user_category_id;
    }

    public void setUser_category_id(int user_category_id) {
        this.user_category_id = user_category_id;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public int getUser_fees() {
        return user_fees;
    }

    public void setUser_fees(int user_fees) {
        this.user_fees = user_fees;
    }

    public String getUser_pdf() {
        return user_pdf;
    }

    public void setUser_pdf(String user_pdf) {
        this.user_pdf = user_pdf;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_dateTime() {
        return user_dateTime;
    }

    public void setUser_dateTime(String user_dateTime) {
        this.user_dateTime = user_dateTime;
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
