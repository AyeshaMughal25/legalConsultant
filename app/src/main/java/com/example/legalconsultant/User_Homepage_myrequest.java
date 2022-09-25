package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.legalconsultant.adapter.CheckRequestStatusBYUser;
import com.example.legalconsultant.adapter.CheckRequestStatusByLawyerAdapter;
import com.example.legalconsultant.lawyer.LawyerRequestActivity;
import com.example.legalconsultant.model.Appointment;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.LawyerCheckRequestStatusService;
import com.example.legalconsultant.service.UserCheckRequestStatusService;
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

public class User_Homepage_myrequest extends AppCompatActivity {

    ListView user_request_LV;
    Spinner user_request_spinner;
    String statues[] = {"Pending", "Accepted", "Rejected", "Completed"};
    List<Request> requestList = new ArrayList<>();
    ProgressDialog progressDialog;
    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__homepage_myrequest);
        user_request_LV = findViewById(R.id.user_request_LV);
        user_request_spinner = findViewById(R.id.user_request_spinner);
        tinyDB = new TinyDB(this);
        ArrayAdapter<String> statuesAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, statues);
        user_request_spinner.setAdapter(statuesAdapter);
        user_request_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ViewRequest("P");
                } else if (position == 1) {
                    ViewRequest("A");
                } else if (position == 2) {
                    ViewRequest("R");
                } else if (position == 3) {
                    ViewRequest("C");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

    }

    private void ViewRequest(String status) {
        requestList.clear();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        LawyerCheckRequestStatusService service = RetrofitClient.getClient().create(LawyerCheckRequestStatusService.class);
        Call<JsonObject> call = service.Check_Status(tinyDB.getInt("CLIENT_ID"));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    if (response.code() == 200){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject data = jsonArray.getJSONObject(i);
                                if (data.getString("status").equals(status)) {
                                    requestList.add(new Request(
                                            data.getString("user_name"),
                                            data.getString("user_cnic"),
                                            data.getString("user_contact"),
                                            data.getString("user_image"),
                                            data.getInt("req_id"),
                                            data.getInt("fk_lawyer_id"),
                                            data.getString("pdf"),
                                            data.getString("status"),
                                            data.getString("date_time")
                                    ));
                                }
                            }

                            CheckRequestStatusBYUser adapter = new CheckRequestStatusBYUser(requestList,
                                    User_Homepage_myrequest.this, 2);
                            user_request_LV.setAdapter(adapter);

                        } catch (JSONException e) {
                            user_request_LV.setAdapter(null);
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(User_Homepage_myrequest.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}