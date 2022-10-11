package com.example.legalconsultant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable {
    @SerializedName("ttt_id")
    @Expose
    private int ttt_id;
    @SerializedName("ttt_day")
    @Expose
    private String ttt_day;
    @SerializedName("ttt_start_time")
    @Expose
    private String ttt_start_time;
    @SerializedName("ttt_end_time")
    @Expose
    private String ttt_end_time;
    @SerializedName("ttt_fk_lawyer_id")
    @Expose
    private int ttt_fk_lawyer_id;
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public Timetable(String ttt_day, String ttt_start_time, String ttt_end_time, int ttt_fk_lawyer_id) {

        this.ttt_day = ttt_day;
        this.ttt_start_time = ttt_start_time;
        this.ttt_end_time = ttt_end_time;
        this.ttt_fk_lawyer_id = ttt_fk_lawyer_id;
    }

    public Timetable() {

    }

    public Timetable(int ttt_id,int fk_id,String ttt_start_time, String ttt_end_time) {
        this.ttt_id = ttt_id;
        this.ttt_fk_lawyer_id = fk_id;
        this.ttt_start_time=ttt_start_time;
        this.ttt_end_time=ttt_end_time;
    }

    public int getTtt_id() {
        return ttt_id;
    }

    public String getTtt_day() {
        return ttt_day;
    }

    public String getTtt_start_time() {
        return ttt_start_time;
    }

    public String getTtt_end_time() {
        return ttt_end_time;
    }

    public int getTtt_fk_lawyer_id() {
        return ttt_fk_lawyer_id;
    }

    public void setTtt_id(int ttt_id) {
        this.ttt_id = ttt_id;
    }

    public void setTtt_day(String ttt_day) {
        this.ttt_day = ttt_day;
    }

    public void setTtt_start_time(String ttt_start_time) {
        this.ttt_start_time = ttt_start_time;
    }

    public void setTtt_end_time(String ttt_end_time) {
        this.ttt_end_time = ttt_end_time;
    }

    public void setTtt_fk_lawyer_id(int ttt_fk_lawyer_id) { this.ttt_fk_lawyer_id = ttt_fk_lawyer_id; }

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
