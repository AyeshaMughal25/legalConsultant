package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.legalconsultant.admin.ManageLawyerActivity;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.ShowProfileDetailService;
import com.example.legalconsultant.service.UpdateUserDataService;
import com.example.legalconsultant.util.EndPoint;
import com.example.legalconsultant.util.TinyDB;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile_user extends AppCompatActivity {
CircleImageView user_image;
EditText user_name,user_email,user_contact,user_cnic;
Button update;
TextView change_psw;
ProgressDialog progressDialog;
    int userID,userCheck;
    String name, email,cnic, contact,image, getStatus;
    TinyDB tinyDB;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_user);
        user_image=findViewById(R.id.selected_user_image);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_contact=findViewById(R.id.user_contact);
        user_cnic=findViewById(R.id.user_cnic);
        change_psw=findViewById(R.id.change_psw);
        update=findViewById(R.id.update);
        tinyDB = new TinyDB(this);
        ShowDetailofUser(tinyDB.getInt("CLIENT_ID"));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata(tinyDB.getInt("CLIENT_ID"));
            }
        });
        change_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(getApplicationContext(),UpdatePasswordActivity.class);
startActivity(intent);
            }
        });

    }

    private void updatedata(int client_id) {
progressDialog=new ProgressDialog(this);
progressDialog.setMessage("Please wait..");
progressDialog.show();
        UpdateUserDataService service=RetrofitClient.getClient().create(UpdateUserDataService.class);
        Call<User> call=service.UpdateUserData(user_name.getText().toString(),user_email.getText().toString(),
                user_contact.getText().toString(),user_cnic.getText().toString(),client_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user=response.body();
                    if(!user.isError()){
                        Toast.makeText(MyProfile_user.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MyProfile_user.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyProfile_user.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowDetailofUser(int client_id) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        ShowProfileDetailService service = RetrofitClient.getClient().create(ShowProfileDetailService.class);
        Call<User> call = service.showprofiledetail(client_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (!user.isError()) {
                        Glide.with(MyProfile_user.this).load(EndPoint.IMAGE_URL + user.getUser_image()).into(user_image);
                        user_name.setText(user.getUser_name());
                        user_email.setText(user.getUser_email());
                        user_contact.setText(user.getUser_contact());
                        user_cnic.setText(user.getUser_cnic());


                    } else {
                        Toast.makeText(MyProfile_user.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyProfile_user.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}