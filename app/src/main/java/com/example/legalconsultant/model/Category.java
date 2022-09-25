package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("cat_id")
    @Expose
    private int cat_id;
    @SerializedName("cat_name")
    @Expose
    private String cat_name;
    @SerializedName("cat_dateTime")
    @Expose
    private String cat_dateTime;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;
    public Category() {

    }

    public Category(int cat_id, String cat_name) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) { this.cat_id = cat_id; }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_dateTime() {return cat_dateTime; }
    public void setCat_dateTime(String user_dateTime) {
        this.cat_dateTime = cat_dateTime;
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

