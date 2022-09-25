package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.lawyer.Manage_AppointmentByLawyer;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Timetable;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.MakeAppointmentSevice;
import com.example.legalconsultant.service.SetTimeTableByLawyerService;
import com.example.legalconsultant.util.TinyDB;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetTimetable extends AppCompatActivity {
    TextView strt_time, endtime;
    int hour, minute;
    TimePickerDialog timePickerDialog;
    Button btn_update;
    Timetable timetable;
    TinyDB tinyDB;
    String Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timetable);
        strt_time = findViewById(R.id.edt_strt_time);
        endtime = findViewById(R.id.edt_endtime);
        btn_update = findViewById(R.id.btn_update);
        tinyDB = new TinyDB(this);
        Day = getIntent().getStringExtra(AppConstants.Day);
        strt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopTimePickerforstarttime();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopTimePicker();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settime();
            }
        });
    }

    private void settime() {
        timetable = new Timetable();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        SetTimeTableByLawyerService service = RetrofitClient.getClient().create(SetTimeTableByLawyerService.class);
        Call<Timetable> call = service.settimetable(Day, strt_time.getText().toString(), endtime.getText().toString(),
                tinyDB.getInt("LAWYER_ID"));
        call.enqueue(new Callback<Timetable>() {
            @Override
            public void onResponse(Call<Timetable> call, Response<Timetable> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    timetable = response.body();
                    if (!timetable.isError()) {
                        Toast.makeText(SetTimetable.this,
                                timetable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SetTimetable.this,
                                timetable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<Timetable> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SetTimetable.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void PopTimePickerforstarttime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
                hour = selectedHoure;
                minute = selectedMinute;
                strt_time.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void PopTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
                hour = selectedHoure;
                minute = selectedMinute;
                endtime.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

}