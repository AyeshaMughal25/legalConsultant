package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.UpdateAppointmentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApptDetailActivity extends AppCompatActivity {
    TextView title, Description, appt_start_time, appt_end_time, date,day;
    EditText edittext;
    Button btn_complete, btn_cancel, btn_submit;
    String getStatus;
    int check, getApptID, getREQID, getAgainstID;
    Appointment appointment;
    String selectedStatus = "";
    ProgressDialog progressDialog;
    LinearLayout btn_click_LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appt_detail);
        check = getIntent().getIntExtra("CHECK", 0);
        getApptID = getIntent().getIntExtra("APPT_ID", 0);
        getREQID = getIntent().getIntExtra("REQ_ID", 0);
        getAgainstID = getIntent().getIntExtra("AGAINST_ID", 0);
        getStatus = getIntent().getStringExtra("STATUS");
        title = findViewById(R.id.title);
        btn_click_LL = findViewById(R.id.btn_click_LL);
        day=findViewById(R.id.day);
        Description = findViewById(R.id.Description);
        appt_start_time = findViewById(R.id.start_time);
        appt_end_time=findViewById(R.id.end_time);
        date = findViewById(R.id.date);
        edittext = findViewById(R.id.edittext);
        btn_complete = findViewById(R.id.btn_complete);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        title.setText(getIntent().getStringExtra("TITLE"));
        Description.setText(getIntent().getStringExtra("DESCRIPTION"));
        appt_start_time.setText(getIntent().getStringExtra("START_TIME"));
        appt_end_time.setText(getIntent().getStringExtra("END_TIME"));
        day.setText(getIntent().getStringExtra("DAY"));
        date.setText(getIntent().getStringExtra("DATE"));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_click_LL.setVisibility(View.VISIBLE);
                selectedStatus = "CA";
            }
        });
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_click_LL.setVisibility(View.VISIBLE);
                selectedStatus = "NA";
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAppt(selectedStatus);
            }
        });

    }

    private void UpdateAppt(String status) {
        appointment = new Appointment();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        UpdateAppointmentService service = RetrofitClient.getClient().create(UpdateAppointmentService.class);
        Call<Appointment> call = service.UpdateAppointment(getApptID, status, edittext.getText().toString());
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    appointment = response.body();
                    if (!appointment.isError()) {
                        Toast.makeText(ApptDetailActivity.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ApptDetailActivity.this,
                                appointment.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ApptDetailActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}