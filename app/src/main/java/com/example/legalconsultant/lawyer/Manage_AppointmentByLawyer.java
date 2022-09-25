package com.example.legalconsultant.lawyer;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.legalconsultant.R;
import com.example.legalconsultant.ViewLawyerTimetable;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.MakeAppointmentSevice;
import com.example.legalconsultant.util.TinyDB;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Manage_AppointmentByLawyer extends AppCompatActivity {
    EditText edt_Title, edt_description,day;
    int hour, minute;
    TimePickerDialog timePickerDialog;
    TextView txt_start_time,txt_end_time, txt_date;
    Button btn_create, btn_view_lawyer_tt ;
    Appointment appointment;
    int clientID, getReqID;
    TinyDB tinyDB;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(this);
        setContentView(R.layout.activity_manage__appointment);
        edt_Title = findViewById(R.id.edt_Title);
        edt_description = findViewById(R.id.edt_description);
        txt_start_time = findViewById(R.id.txt_start_time);
        txt_end_time = findViewById(R.id.txt_end_time);
        day=findViewById(R.id.edt_l_day);
        txt_date = findViewById(R.id.txt_date);
        btn_view_lawyer_tt=findViewById(R.id.btn_view_lawyer_timetable);
        /*Test github*/
        initDatePicker();
        btn_create = findViewById(R.id.btn_create);
        clientID = getIntent().getIntExtra("CLIENT_ID", 0);
        getReqID = getIntent().getIntExtra("REQUEST_ID", 0);
        btn_view_lawyer_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manage_AppointmentByLawyer.this, ViewLawyerTimetable.class);
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
                PopTimePickerEndTime();
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

    private void PopTimePickerEndTime() {

            TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
                    hour=selectedHoure;
                    minute=selectedMinute;
                    txt_end_time.setText(String.format(Locale.getDefault(),"%2d:%2d",hour, minute));
                }
            };
            TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour,minute,true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        }



    private void initDatePicker() {
        DatePickerDialog .OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
String date= makeDateString(dayOfMonth,month,year);
txt_date.setText(date);
            }
        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog= new DatePickerDialog(this,dateSetListener,year,month,day);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
    return getMonthFormat( month)+" "+dayOfMonth+" "+ year;
    }

    private String getMonthFormat( int month) {
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

return "JAN"; }


    private void SetAppointment() {
        appointment = new Appointment();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        MakeAppointmentSevice service = RetrofitClient.getClient().create(MakeAppointmentSevice.class);
        Call<Appointment> call = service.makeappointment(edt_Title.getText().toString(), edt_description.getText().toString(),
                tinyDB.getInt("LAWYER_ID"), clientID, getReqID, txt_start_time.getText().toString(),
                txt_end_time.getText().toString(),day.getText().toString(),txt_date.getText().toString());
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    appointment = response.body();
                    if (!appointment.isError()) {
                        Toast.makeText(Manage_AppointmentByLawyer.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Manage_AppointmentByLawyer.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Manage_AppointmentByLawyer.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void PopTimePicker() {
    TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int selectedHoure, int selectedMinute) {
hour=selectedHoure;
minute=selectedMinute;
            txt_start_time.setText(String.format(Locale.getDefault(),"%2d:%2d",hour, minute));
        }
    };
    TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour,minute,true);
    timePickerDialog.setTitle("Select Time");
    timePickerDialog.show();
    }


    public void OpenDatePicker() {
        datePickerDialog.show();
    }
}
