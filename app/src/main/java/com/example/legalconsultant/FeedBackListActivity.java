package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.CourtAdapter;
import com.example.legalconsultant.adapter.FeedbackListAdapter;
import com.example.legalconsultant.adapter.ViewAppoinmentAdaptor;
import com.example.legalconsultant.admin.ManageCourtsActivity;
import com.example.legalconsultant.lawyer.ViewApptLawyerActivity;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.model.FeedBack;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.GetCourtService;
import com.example.legalconsultant.service.ShowAllFeedbacktolawyerService;
import com.example.legalconsultant.service.ViewAppointmentByLawyerService;
import com.example.legalconsultant.util.TinyDB;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackListActivity extends AppCompatActivity {
ListView listView;
    List<FeedBack> FeedBackList = new ArrayList<>();
    ProgressDialog progressDialog;
    TinyDB tinyDB;
    int lawyer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_list);
        tinyDB=new TinyDB(this);
        listView=findViewById(R.id.lv_feedback);
        lawyer_id= tinyDB.getInt("LAWYER_ID");
        Getfeedback();
    }

    private void Getfeedback() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        ShowAllFeedbacktolawyerService service = RetrofitClient.getClient().create(ShowAllFeedbacktolawyerService.class);
        Call<JsonObject> call = service.getallfeedback(lawyer_id);
        call.enqueue(new Callback<JsonObject>() {
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
                                FeedBackList.add(new FeedBack(
                                        data.getInt("id"),
                                        data.getString("case_subject"),
                                        data.getString("feedback"),
                                        data.getInt("rating")
                                ));


                            }

                            FeedbackListAdapter adapter = new FeedbackListAdapter(FeedBackList, FeedBackListActivity.this);
                            listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedBackListActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
