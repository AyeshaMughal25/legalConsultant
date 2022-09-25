package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.ViewAppoinmentAdaptor;
import com.example.legalconsultant.lawyer.ViewApptLawyerActivity;
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

public class ViewApptUserActivity extends AppCompatActivity {
    ListView User_appt_LV;
    List<Appointment> appointmentList= new ArrayList<>();
    TinyDB tinyDB;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appt_user);
        User_appt_LV=findViewById(R.id.User_appt_LV);
        tinyDB = new TinyDB(this);

    }


}