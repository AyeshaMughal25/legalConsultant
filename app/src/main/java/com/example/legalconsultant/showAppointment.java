package com.example.legalconsultant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.legalconsultant.adapter.TimetableAdapter;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Timetable;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.showAppointmentService;
import com.example.legalconsultant.util.TinyDB;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showAppointment extends AppCompatActivity {
    ListView LV_TT;
    List<Timetable> timetablesList = new ArrayList<>();
    List<Appointment> apptList = new ArrayList<>();
    ProgressDialog progressDialog;
    TinyDB tinyDB;
    String Day;
    int getFkLawyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment);
        tinyDB = new TinyDB(this);
        LV_TT = findViewById(R.id.lv_tt_mon);
        Day = getIntent().getStringExtra(AppConstants.Day);
        getFkLawyerID = getIntent().getIntExtra("LAWYER_ID", 0);
        showtimetable(getFkLawyerID);
    }

    private void showtimetable(int getFkLawyerID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        showAppointmentService service = RetrofitClient.getClient().create(showAppointmentService.class);
        Call<JsonObject> call = service.showtimetable(Day, getFkLawyerID);
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
                                {
                                    timetablesList.add(new Timetable(
                                            data.getInt("ttt_id"),
                                            data.getInt("ttt_fk_lawyer_id"),
                                            data.getString("ttt_start_time"),
                                            data.getString("ttt_end_time")
                                    ));
                                }
                            }
                            TimetableAdapter adapter = new TimetableAdapter(timetablesList,
                                    showAppointment.this);
                            LV_TT.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            LV_TT.setAdapter(null);
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(showAppointment.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}