package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.LoginService;
import com.example.legalconsultant.util.TinyDB;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView move;
    EditText mail, pswd2;
    ProgressDialog progressDialog;
    User user;
    Button button;
    String userContact;
    TinyDB tinyDB;
    int userid;
    CircleImageView HelpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tinyDB = new TinyDB(this);
        move = (TextView) findViewById(R.id.sigup);
        mail = findViewById(R.id.mail);
        pswd2 = findViewById(R.id.pswd2);
        button = findViewById(R.id.button);
        HelpButton=findViewById(R.id.help_button);

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });
        HelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=+923155490335";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.getText().toString().isEmpty()) {
                    mail.setError("Feild cannot be empty");
                }
                else if (pswd2.getText().toString().isEmpty()){
                    pswd2.setError("Field cannot be empty");
                }
                        else {
                    LoginUser();
                }
            }
        });


    }


    public void LoginUser() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();

        LoginService service = RetrofitClient.getClient().create(LoginService.class);
        Call<User> call = service.loginUser(mail.getText().toString(),
                pswd2.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (!user.isError()) {
                        switch (user.getUser_type()) {
                            case "A": {
                                Intent intent = new Intent(LoginActivity.this, Admin_Dashboard.class);
                                startActivity(intent);
                                break;
                            }
                            case "L": {
                                tinyDB.putInt("LAWYER_ID", user.getUser_id());
                                tinyDB.putString(AppConstants.lawyer_pass, pswd2.getText().toString().trim());
                                Intent intent = new Intent(LoginActivity.this, LawyerDashboard.class);
                                startActivity(intent);
                                break;
                            }
                            case "C": {
                                tinyDB.putInt("CLIENT_ID", user.getUser_id());
                                tinyDB.putString(AppConstants.Client_pass, pswd2.getText().toString().trim());
                                Intent intent = new Intent(LoginActivity.this, UserProfile.class);
                                startActivity(intent);
                                break;
                            }
                            default:
                                Toast.makeText(LoginActivity.this,
                                        user.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}