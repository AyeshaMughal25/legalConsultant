package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.UpdatePasswordService;
import com.example.legalconsultant.util.TinyDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordByLawyer extends AppCompatActivity {
    Button updatepsw;
    EditText pssword;
    TinyDB tinyDB;
    User user;
    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_by_lawyer);
        tinyDB=new TinyDB(UpdatePasswordByLawyer.this);
       Password = tinyDB.getString(AppConstants.lawyer_pass);
        pssword=findViewById(R.id.pswd);
        updatepsw=findViewById(R.id.update_psw);
       pssword.setText(Password);
        updatepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepsw(tinyDB.getInt("LAWYER_ID"));
            }
        });
    }

    private void updatepsw(int lawyer_id) {
        user =new User();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        UpdatePasswordService service= RetrofitClient.getClient().create(UpdatePasswordService.class);
        Call<User> call=service.UpdatePassword(pssword.getText().toString(),lawyer_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (!user.isError()) {
                        Toast.makeText(UpdatePasswordByLawyer.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdatePasswordByLawyer.this,
                                user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdatePasswordByLawyer.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }
}