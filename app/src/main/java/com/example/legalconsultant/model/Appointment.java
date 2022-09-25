package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {
    @SerializedName("appt_id")
    @Expose
    private int appt_id;
    @SerializedName("appt_title")
    @Expose
    private String appt_title;
    @SerializedName("appt_description")
    @Expose
    private String appt_description;
    @SerializedName("fk_lawyer_id")
    @Expose
    private int fk_lawyer_id;
    @SerializedName("fk_customer_id")
    @Expose
    private int fk_customer_id;
    @SerializedName("fk_request_id")
    @Expose
    private int fk_request_id;

    @SerializedName("appt_start_time")
    @Expose
    private String appt_start_time;
    @SerializedName("appt_end_time")
    @Expose
    private String appt_end_time;
    @SerializedName("appt_date")
    @Expose
    private String appt_date;

    @SerializedName("appt_status")
    @Expose
    private String appt_status;

    @SerializedName("created_Date_Time")
    @Expose
    private String created_Date_Time;
    @SerializedName("appt_day")
    @Expose
    private String appt_day;
    @SerializedName("appt_reason")
    @Expose
    private String appt_reason;
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;


    public Appointment() {

    }

    public Appointment(String appt_start_time, String appt_end_time) {
        this.appt_start_time = appt_start_time;
        this.appt_end_time = appt_end_time;
    }

    public Appointment(int appt_id, String appt_title, String appt_description,
                       int fk_customer_id, int fk_request_id, String appt_start_time, String appt_end_time, String appt_day,String appt_date, String appt_status, String created_Date_Time) {
        this.appt_id = appt_id;
        this.appt_title = appt_title;
        this.appt_description = appt_description;
        this.fk_customer_id = fk_customer_id;
        this.fk_request_id = fk_request_id;
        this.appt_start_time = appt_start_time;
        this.appt_end_time=appt_end_time;
        this.appt_day=appt_day;
        this.appt_date = appt_date;
        this.appt_status = appt_status;
        this.created_Date_Time = created_Date_Time;
    }

    public String getAppt_start_time() {
        return appt_start_time;
    }

    public void setAppt_start_time(String appt_start_time) {
        this.appt_start_time = appt_start_time;
    }

    public String getAppt_day() {
        return appt_day;
    }

    public void setAppt_day(String appt_day) {
        this.appt_day = appt_day;
    }

    public String getAppt_end_time() {
        return appt_end_time;
    }

    public void setAppt_end_time(String appt_end_time) {
        this.appt_end_time = appt_end_time;
    }

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public String getAppt_title() {
        return appt_title;
    }

    public void setAppt_title(String appt_title) {
        this.appt_title = appt_title;
    }

    public String getAppt_description() {
        return appt_description;
    }

    public void setAppt_description(String appt_description) {
        this.appt_description = appt_description;
    }

    public int getFk_lawyer_id() {
        return fk_lawyer_id;
    }

    public void setFk_lawyer_id(int fk_lawyer_id) {
        this.fk_lawyer_id = fk_lawyer_id;
    }

    public int getFk_customer_id() {
        return fk_customer_id;
    }

    public void setFk_customer_id(int fk_customer_id) {
        this.fk_customer_id = fk_customer_id;
    }

    public int getFk_request_id() {
        return fk_request_id;
    }

    public void setFk_request_id(int fk_request_id) {
        this.fk_request_id = fk_request_id;
    }

    public String getAppt_date() {
        return appt_date;
    }

    public void setAppt_date(String appt_date) {
        this.appt_date = appt_date;
    }

    public String getAppt_status() {
        return appt_status;
    }

    public void setAppt_status(String appt_status) {
        this.appt_status = appt_status;
    }

    public String getCreated_Date_Time() {
        return created_Date_Time;
    }

    public void setCreated_Date_Time(String created_Date_Time) {
        this.created_Date_Time = created_Date_Time;
    }
    public String getAppt_reason() {
        return appt_reason;
    }

    public void setAppt_reason(String appt_reason) {
        this.appt_reason = appt_reason;
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
