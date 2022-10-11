package com.example.legalconsultant.lawyer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.adapter.ViewAppoinmentAdaptor;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.ViewAppointmentByLawyerService;
import com.example.legalconsultant.service.ViewAppointmentByUserService;
import com.example.legalconsultant.util.TinyDB;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewApptLawyerActivity extends AppCompatActivity {

    ListView Lawyer_appt_LV;
    List<Appointment> appointmentList = new ArrayList<>();
    TinyDB tinyDB;
    ProgressDialog progressDialog;
    int getReqID, getCHeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt_lawyer);
        Lawyer_appt_LV = findViewById(R.id.Lawyer_appt_LV);
        tinyDB = new TinyDB(this);
        getReqID = getIntent().getIntExtra("REQUEST_ID", 0);
        getCHeck = getIntent().getIntExtra("CHECK", 0);
        if (getCHeck == 2) {
            showallappointments(tinyDB.getInt("LAWYER_ID"));
        } else {
            ShowCustomerAppt();
        }

    }

    private void showallappointments(int LAWYER_ID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        ViewAppointmentByLawyerService service = RetrofitClient.getClient().create(ViewAppointmentByLawyerService.class);
        Call<JsonObject> call = service.getallappoinment(LAWYER_ID);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                if (data.getInt("fk_request_id") == getReqID) {
                                    appointmentList.add(new Appointment(
                                            data.getInt("appt_id"),
                                            data.getInt("ttt_id"),
                                            data.getString("appt_title"),
                                            data.getString("appt_description"),
                                            data.getInt("fk_customer_id"),
                                            data.getInt("fk_request_id"),
                                            data.getString("appt_start_time"),
                                            data.getString("appt_end_time"),
                                            data.getString("appt_day"),
                                            data.getString("appt_date"),
                                            data.getString("appt_status"),
                                            data.getString("created_Date_Time")
                                    ));
                                }
                            }
                            ViewAppoinmentAdaptor adaptor = new ViewAppoinmentAdaptor(appointmentList,
                                    ViewApptLawyerActivity.this, getCHeck);
                            Lawyer_appt_LV.setAdapter(adaptor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewApptLawyerActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ShowCustomerAppt() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        ViewAppointmentByUserService service = RetrofitClient.getClient().create(ViewAppointmentByUserService.class);
        Call<JsonObject> call = service.getallappoinment(tinyDB.getInt("CLIENT_ID"));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                if (data.getInt("fk_request_id") == getReqID) {
                                    appointmentList.add(new Appointment(
                                            data.getInt("appt_id"),
                                            data.getInt("ttt_id"),
                                            data.getString("appt_title"),
                                            data.getString("appt_description"),
                                            data.getInt("fk_customer_id"),
                                            data.getInt("fk_request_id"),
                                            data.getString("appt_start_time"),
                                            data.getString("appt_end_time"),
                                            data.getString("appt_day"),
                                            data.getString("appt_date"),
                                            data.getString("appt_status"),
                                            data.getString("created_Date_Time")
                                    ));
                                }
                            }

                            ViewAppoinmentAdaptor adaptor = new ViewAppoinmentAdaptor(appointmentList,
                                    ViewApptLawyerActivity.this, getCHeck);
                            Lawyer_appt_LV.setAdapter(adaptor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewApptLawyerActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}