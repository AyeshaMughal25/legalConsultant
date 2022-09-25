package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.admin.ManageLawyerActivity;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.UpdateUserStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageLawyerProfile extends AppCompatActivity {
    Button accept, block;
    TextView username, useremail, usercnic, usercontact;
    ImageView img;
    String userType, userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lawyer_profile);
        accept = findViewById(R.id.accept);
        block = findViewById(R.id.block);
        username = findViewById(R.id.lawyername);
        useremail = findViewById(R.id.lawyermail);
        usercnic = findViewById(R.id.lawyercnic);
        usercontact = findViewById(R.id.lawyercontct);
        username.setText(getIntent().getStringExtra("USER_NAME"));
        useremail.setText(getIntent().getStringExtra("USER_EMAIL"));
        usercontact.setText(getIntent().getStringExtra("USER_CONTACT"));
        usercnic.setText(getIntent().getStringExtra("USER_CNIC"));
        userType = getIntent().getStringExtra("USER_TYPE");
        userStatus = getIntent().getStringExtra("USER_STATUS");


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserStatus("A");
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserStatus("B");
            }
        });
        if (userStatus.equals("A")) {
            accept.setVisibility(View.GONE);
        } else if (userStatus.equals("B")) {
            block.setVisibility(View.GONE);
        }
    }

    public void UpdateUserStatus(String status) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        UpdateUserStatus service = RetrofitClient.getClient().create(UpdateUserStatus.class);
        Call<User> call = service.UpdateStatus(status, getIntent().getIntExtra("USER_ID", 0));
        call.enqueue(new Callback<User>() {
                @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    User user = response.body();
                    if (!user.isError()) {
                        Toast.makeText(ManageLawyerProfile.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                           ManageLawyerActivity.class));
                    } else {
                        Toast.makeText(ManageLawyerProfile.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ManageLawyerProfile.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}