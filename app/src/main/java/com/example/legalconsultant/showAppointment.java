package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.TimetableAdapter;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Timetable;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.GetAllAppointmentForMatchingService;
import com.example.legalconsultant.service.MakeAppointmentSevice;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment);
        LV_TT = findViewById(R.id.lv_tt_mon);
        tinyDB = new TinyDB(this);
        Day = getIntent().getStringExtra(AppConstants.Day);
        showtimetable();
    }

    private void showtimetable() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        showAppointmentService service = RetrofitClient.getClient().create(showAppointmentService.class);
        Call<JsonObject> call = service.showtimetable(Day, tinyDB.getInt("LAWYER_ID"));
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
                                            data.getInt("ttt_fk_lawyer_id"),
                                            data.getString("ttt_start_time"),
                                            data.getString("ttt_end_time")
                                    ));
                                }
                            }
                            GetAllAppointmentForMatchingService service1 = RetrofitClient.getClient().create(GetAllAppointmentForMatchingService.class);   //TODO mke app cal here
                            Call<JsonObject> call1 = service1.getallappoinmentformatching(Day, tinyDB.getInt("LAWYER_ID"));
                            call1.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                                        JSONArray jsonArray = jsonObject.getJSONArray("records");


                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject data = jsonArray.getJSONObject(i);

                                            {
                                                apptList.add(new Appointment(
                                                        data.getInt("fk_lawyer_id"),
                                                        data.getString("appt_start_time"),
                                                        data.getString("appt_end_time")
                                                ));
                                            }
                                        }
                                        ArrayList<Timetable> tempTimeTabe = new ArrayList<>();
                                        for (int i = 0; i < timetablesList.size(); i++) {
                                            int timeTblLwId = timetablesList.get(i).getTtt_fk_lawyer_id();
                                            String tt_start_time=timetablesList.get(i).getTtt_start_time();
                                            String tt_end_time=timetablesList.get(i).getTtt_end_time();

                                            for (int j = 0; j < apptList.size(); j++) {
                                                int apptTblLwId = apptList.get(j).getFk_lawyer_id();
                                                String appt_start_time=apptList.get(j).getAppt_start_time();
                                                String appt_end_time=apptList.get(j).getAppt_end_time();
                                                if (timeTblLwId == apptTblLwId) {
                                                    if (!tt_start_time.trim().equals(appt_start_time.trim()) && !tt_end_time.trim().equals(appt_end_time.trim())) {
                                                        tempTimeTabe.add(timetablesList.get(i));
                                                        timetablesList.remove(i);
                                                    }
                                                }
                                            }
                                        }
                                        TimetableAdapter adapter = new TimetableAdapter(tempTimeTabe,
                                                showAppointment.this);
                                        LV_TT.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception ex) {

                                    }
                                    /*if (!timetablesList.get(i).getTtt_start_time().equals(apptList.get(i).getAppt_start_time()) &&
                                            timetablesList.get(i).getTtt_end_time().equals(apptList.get(i).getAppt_end_time())) {
                                        tempTimeTabe.add(timetablesList.get(i));

                                    }*/
                                    Log.i("APPT_DATA", response.body().toString());

                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });


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