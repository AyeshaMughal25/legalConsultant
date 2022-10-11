package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.legalconsultant.admin.UpdateDeleteCourtdetail;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.ShowProfileDetailService;
import com.example.legalconsultant.service.UpdateLawyerDataService;
import com.example.legalconsultant.service.UpdateUserDataService;
import com.example.legalconsultant.util.EndPoint;
import com.example.legalconsultant.util.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_profile_Lawyer extends AppCompatActivity {
    ImageView selected_lawyer_image;
    EditText edt_lawyer_name, edt_Lawyer_email, edt_lawyer_contact, edt_lawyer_cnic, lawyer_fee, edt_lawyer_city;
    TextView time, change_psw;
    Button start_time, end_time, update;
    ProgressDialog progressDialog;
    User user;
    String Password;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile__lawyer);
        tinyDB = new TinyDB(My_profile_Lawyer.this);
        selected_lawyer_image = findViewById(R.id.selected_lawyer_image);
        edt_lawyer_name = findViewById(R.id.edt_lawyer_name);
        edt_Lawyer_email = findViewById(R.id.edt_Lawyer_email);
        edt_lawyer_contact = findViewById(R.id.edt_lawyer_contact);
        edt_lawyer_cnic = findViewById(R.id.edt_lawyer_cnic);
        edt_lawyer_city = findViewById(R.id.edt_lawyer_city);
        change_psw=findViewById(R.id.change_psw);
        lawyer_fee=findViewById(R.id.lawyer_fee);
        time = findViewById(R.id.time);
        update = findViewById(R.id.update);
        ShowDetailofUser(tinyDB.getInt("LAWYER_ID"));
        change_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UpdatePasswordByLawyer.class);
                startActivity(intent);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(tinyDB.getInt("LAWYER_ID"));
            }
        });
    }

    private void updateData(int lawyer_id) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        UpdateLawyerDataService service=RetrofitClient.getClient().create(UpdateLawyerDataService.class);
        Call<User> call=service.UpdateLawyerDetail(edt_lawyer_name.getText().toString(),edt_Lawyer_email.getText().toString(),
                edt_lawyer_contact.getText().toString(),edt_lawyer_cnic.getText().toString(),
               Integer.parseInt(lawyer_fee.getText().toString()), edt_lawyer_city.getText().toString(),lawyer_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user=response.body();
                    if(!user.isError()){
                        Toast.makeText(My_profile_Lawyer.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(My_profile_Lawyer.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(My_profile_Lawyer.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void ShowDetailofUser(int User_ID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        ShowProfileDetailService service = RetrofitClient.getClient().create(ShowProfileDetailService.class);
        Call<User> call = service.showprofiledetail(User_ID);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (!user.isError()) {
                        Glide.with(My_profile_Lawyer.this).load(EndPoint.IMAGE_URL + user.getUser_image()).into(selected_lawyer_image);
                        edt_lawyer_name.setText(user.getUser_name());
                        edt_Lawyer_email.setText(user.getUser_email());
                        edt_lawyer_contact.setText(user.getUser_contact());
                        edt_lawyer_cnic.setText(user.getUser_cnic());
                        edt_lawyer_city.setText(user.getUser_city());
                        lawyer_fee.setText(String.valueOf(user.getUser_fees()));

                    } else {
                        Toast.makeText(My_profile_Lawyer.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(My_profile_Lawyer.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}