package com.example.legalconsultant.lawyer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.model.FeedBack;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.UpdateRequestService;
import com.example.legalconsultant.util.EndPoint;
import com.example.legalconsultant.util.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerRequestDetailActivity extends AppCompatActivity {

    TextView name, cnic, contact, pdf, status;
    Button accept, reject, Chat, manage_appointment, Complete, View_all_appointment;
    int getRequestID, getFkCientID;
    String userName, userCnic, userContact, getPDF, getStatus;
    Request request;
    int userCheck;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_request_detail);
        tinyDB=new TinyDB(this);
        name = findViewById(R.id.name);
        cnic = findViewById(R.id.cnic);
        contact = findViewById(R.id.contact);
        pdf = findViewById(R.id.pdf);
        status = findViewById(R.id.status);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);
        Complete = findViewById(R.id.Complete);
        Chat = findViewById(R.id.Chat);
        View_all_appointment = findViewById(R.id.View_all_appointment);
        manage_appointment = findViewById(R.id.manage_appointment);

        getRequestID = getIntent().getIntExtra("REQUEST_ID", 0);
        getFkCientID = getIntent().getIntExtra("FK_CLIENT_ID", 0);
        userCheck = getIntent().getIntExtra("CHECK", 0);
        userName = getIntent().getStringExtra("USER_NAME");
        userCnic = getIntent().getStringExtra("USER_CNIC");
        getStatus = getIntent().getStringExtra("STATUS");
        getPDF = getIntent().getStringExtra("PDF");
        userContact = getIntent().getStringExtra("USER_CONTACT");
        name.setText(userName);
        cnic.setText(userCnic);
        contact.setText(userContact);
        pdf.setText(getPDF);
        pdf.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(EndPoint.IMAGE_URL + getPDF));
            startActivity(browserIntent);
        });
        View_all_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewApptLawyerActivity.class);
                intent.putExtra("REQUEST_ID", getRequestID);
                intent.putExtra("CHECK", userCheck);
                startActivity(intent);
            }
        });
        manage_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Manage_AppointmentByLawyer.class);
                intent.putExtra("CLIENT_ID", getFkCientID);
                intent.putExtra("REQUEST_ID", getRequestID);
                startActivity(intent);
            }
        });
        if (userCheck == 2) {
            if (getStatus.equals("P")) {
                status.setText("Pending");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.GONE);
                manage_appointment.setVisibility(View.GONE);
                View_all_appointment.setVisibility(View.GONE);
                accept.setVisibility(View.VISIBLE);
                reject.setVisibility(View.VISIBLE);
            } else if (getStatus.equals("A")) {
                status.setText("Accepted");
                Complete.setVisibility(View.VISIBLE);
                Chat.setVisibility(View.VISIBLE);
                manage_appointment.setVisibility(View.VISIBLE);
                View_all_appointment.setVisibility(View.VISIBLE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            } else if (getStatus.equals("C")) {
                status.setText("Completed");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.VISIBLE);
                manage_appointment.setVisibility(View.VISIBLE);
                View_all_appointment.setVisibility(View.VISIBLE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            } else if (getStatus.equals("R")) {
                status.setText("Rejected");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.GONE);
                manage_appointment.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                View_all_appointment.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }
        } else if (userCheck == 1) {
            if (getStatus.equals("P")) {
                status.setText("Pending");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.GONE);
                manage_appointment.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            } else if (getStatus.equals("A")) {
                status.setText("Accepted");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.VISIBLE);
                manage_appointment.setVisibility(View.VISIBLE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            } else if (getStatus.equals("C")) {
                status.setText("Completed");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.VISIBLE);
                manage_appointment.setVisibility(View.VISIBLE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            } else if (getStatus.equals("R")) {
                status.setText("Rejected");
                Complete.setVisibility(View.GONE);
                Chat.setVisibility(View.GONE);
                manage_appointment.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
            }

        }


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updaterequeststatus("A");

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updaterequeststatus("R");

            }
        });

        Complete.setOnClickListener(v ->
                updaterequeststatus("C"));
        Chat.setOnClickListener(v -> {
            String url = "https://api.whatsapp.com/send?phone="+userContact;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void updaterequeststatus(String status) {
        request = new Request();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait....");
        progressDialog.show();
        UpdateRequestService service = RetrofitClient.getClient().create(UpdateRequestService.class);
        Call<Request> call = service.UpdateRequestStatus(status, getIntent().getIntExtra("REQUEST_ID", 0));
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    request = response.body();
                    if (!request.isError()) {
                        Toast.makeText(LawyerRequestDetailActivity.this, request.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LawyerRequestDetailActivity.this, request.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LawyerRequestDetailActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LawyerRequestDetailActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}