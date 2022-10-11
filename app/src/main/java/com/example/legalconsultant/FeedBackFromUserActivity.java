package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.legalconsultant.adapter.GetlawyerByCatAdapter;
import com.example.legalconsultant.model.FeedBack;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.FeedBackFromUserService;
import com.example.legalconsultant.service.GetNoofRatingService;
import com.example.legalconsultant.util.TinyDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackFromUserActivity extends AppCompatActivity {
    EditText Subject, feedback;
    RatingBar rating;
    TinyDB tinyDB;
    int getRequestID,getfklawyerid;
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
        Call<FeedBack> call = service.feedbackfromuser(getRequestID, tinyDB.getInt("CLIENT_ID"), tinyDB.getInt("LAWYER_ID"),
                Subject.getText().toString(), feedback.getText().toString(), Integer.valueOf(rating));

        call.enqueue(new Callback<FeedBack>() {
            @Override
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    FeedBack = response.body();
                    if (!FeedBack.isError()) {
                        GetNoofRatingService service1 = RetrofitClient.getClient().create(GetNoofRatingService.class);
                        Call<JsonObject> call1 = service1.getallratingno(getfklawyerid);
                        call1.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    if (response.code() == 200) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                                            JSONArray jsonArray = jsonObject.getJSONArray("records");

                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject data = jsonArray.getJSONObject(i);
                                                {
                                                    ratingCount += data.getInt("Rating");
                                                }
                                            }
                                            Toast.makeText(FeedBackFromUserActivity.this, ratingCount+"", Toast.LENGTH_SHORT).show();                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });
                        Toast.makeText(FeedBackFromUserActivity.this,
                                FeedBack.getMessage(), Toast.LENGTH_SHORT).show();
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