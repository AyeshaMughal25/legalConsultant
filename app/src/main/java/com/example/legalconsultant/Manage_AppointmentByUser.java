package com.example.legalconsultant;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.MakeAppointmentSevice;
import com.example.legalconsultant.util.TinyDB;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_AppointmentByUser extends AppCompatActivity {
    EditText edt_Title, edt_description, appt_day;
    int hour, minute;
    TimePickerDialog timePickerDialog;
    TextView txt_start_time, txt_end_time, txt_date;
    Button btn_create, View_all_appointment, btn_view_lawyer_tt;

    Appointment appointment;
    int lawyerID, getReqID, tt_id;
    TinyDB tinyDB;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__appointment_by_user);
        edt_Title = findViewById(R.id.edt_Title);
        edt_description = findViewById(R.id.edt_description);
        txt_start_time = findViewById(R.id.txt_start_time);
        appt_day = findViewById(R.id.edt_day);
        txt_end_time = findViewById(R.id.txt_end_time);
        txt_date = findViewById(R.id.txt_date);
        btn_view_lawyer_tt = findViewById(R.id.btn_view_lawyer_tt);
        initDatePicker();
        btn_create = findViewById(R.id.btn_create);
        tinyDB = new TinyDB(this);
        lawyerID = getIntent().getIntExtra("LAWYER_ID", 0);
        tinyDB.putInt("LAWYER_ID", lawyerID);
        //getReqID = getIntent().getIntExtra("REQUEST_ID", 0);
        getReqID = tinyDB.getInt("REQUEST_ID");
        tt_id = getIntent().getIntExtra("Timetable_ID", 0);
        btn_view_lawyer_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manage_AppointmentByUser.this, ViewLawyerTimetable.class);
                startActivity(intent);
            }
        });
        txt_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopTimePicker();
            }
        });
        txt_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopTimePickerendtime();
            }
        });
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDatePicker();

            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_Title.getText().toString().isEmpty()) {
                    edt_Title.setError("Feild cannot be empty");
                } else if (edt_description.getText().toString().isEmpty()) {
                    edt_description.setError("Feild cannot be empty");
                } else if (txt_start_time.getText().toString().isEmpty()) {
                    txt_start_time.setError("SET a time");
                } else if (txt_date.getText().toString().isEmpty()) {
                    txt_date.setError("Choose a Date");
                } else {
                    SetAppointment();
                }
            }
        });

    }

    private void PopTimePickerendtime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
                hour = selectedHoure;
                minute = selectedMinute;
                txt_end_time.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void SetAppointment() {
        appointment = new Appointment();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        MakeAppointmentSevice service = RetrofitClient.getClient().create(MakeAppointmentSevice.class);
        Call<Appointment> call = service.makeappointment(tt_id, edt_Title.getText().toString(), edt_description.getText().toString()
                , lawyerID, tinyDB.getInt("CLIENT_ID"), getReqID, txt_start_time.getText().toString(),
                txt_end_time.getText().toString(), appt_day.getText().toString(), txt_date.getText().toString());
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    appointment = response.body();
                    if (!appointment.isError()) {
                        Toast.makeText(Manage_AppointmentByUser.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Manage_AppointmentByUser.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Manage_AppointmentByUser.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                txt_date.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";

        if (month == 2)
            return "FEB";

        if (month == 3)
            return "MAR";

        if (month == 4)
            return "APR";

        if (month == 5)
            return "MAY";

        if (month == 6)
            return "JUN";

        if (month == 7)
            return "JUL";

        if (month == 8)
            return "AUG";

        if (month == 9)
            return "SEP";

        if (month == 10)
            return "OCT";

        if (month == 11)
            return "NOV";

        if (month == 12)
            return "DEC";

        return "JAN";
    }

    public void PopTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
                hour = selectedHoure;
                minute = selectedMinute;
                txt_start_time.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    public void OpenDatePicker() {
        datePickerDialog.show();
    }
}

