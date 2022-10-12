package com.example.legalconsultant;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.legalconsultant.model.FeedBack;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.FeedBackFromUserService;
import com.example.legalconsultant.util.TinyDB;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFromUserActivity extends AppCompatActivity {
    EditText Subject, feedback;
    RatingBar rating;
    TinyDB tinyDB;
    int getRequestID, getfklawyerid;
    Button btn_feedback;
    FeedBack FeedBack;
    ArrayList<String> arrIds = new ArrayList<>();
    List<Request> requestList = new ArrayList<>();
    int ratingCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_from_user);
        tinyDB = new TinyDB(this);
        Subject = findViewById(R.id.Subject);
        feedback = findViewById(R.id.feedback);
        btn_feedback = findViewById(R.id.btn_feedback);
        getRequestID = getIntent().getIntExtra("REQUEST_ID", 0);
        getfklawyerid = getIntent().getIntExtra("LAWYER_ID", 0);
        rating = findViewById(R.id.Rating);
        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfeedbackfromuser();
            }
        });
    }

    private void getfeedbackfromuser() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        float f = rating.getRating();
        int i = (int) f;
        String rating = (i == f) ? String.valueOf(i) : String.valueOf(f);
        FeedBackFromUserService service = RetrofitClient.getClient().create(FeedBackFromUserService.class);
        Call<FeedBack> call = service.feedbackfromuser(getRequestID, tinyDB.getInt("CLIENT_ID"), getfklawyerid,
                Subject.getText().toString(), feedback.getText().toString(), Integer.valueOf(rating));

        call.enqueue(new Callback<FeedBack>() {
            @Override
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    FeedBack = response.body();
                    if (!FeedBack.isError()) {
                        Toast.makeText(FeedBackFromUserActivity.this,
                                FeedBack.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(FeedBackFromUserActivity.this,
                                FeedBack.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<FeedBack> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedBackFromUserActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}