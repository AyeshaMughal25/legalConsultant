package com.example.legalconsultant.lawyer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.adapter.CheckRequestStatusByLawyerAdapter;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.LawyerRequestService;
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

public class LawyerRequestActivity extends AppCompatActivity {

    ListView lawyer_request_LV;
    Spinner lawyer_request_spinner;
    String statues[] = {"Pending", "Accepted", "Rejected", "Completed"};
    List<Request> requestList = new ArrayList<>();
    ProgressDialog progressDialog;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_request);
        lawyer_request_LV = findViewById(R.id.lawyer_request_LV);
        lawyer_request_spinner = findViewById(R.id.lawyer_request_spinner);
        tinyDB = new TinyDB(this);
        ArrayAdapter<String> statuesAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, statues);
        lawyer_request_spinner.setAdapter(statuesAdapter);
        lawyer_request_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        LawyerRequestService service = RetrofitClient.getClient().create(LawyerRequestService.class);
        Call<JsonObject> call = service.getLawyerRequest(tinyDB.getInt("LAWYER_ID"));
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
                                if (data.getString("status").equals(status)) {
                                    requestList.add(new Request(
                                            data.getString("user_name"),
                                            data.getString("user_cnic"),
                                            data.getString("user_contact"),
                                            data.getString("user_image"),
                                            data.getInt("req_id"),
                                            data.getInt("fk_client_id"),
                                            data.getString("pdf"),
                                            data.getString("status"),
                                            data.getString("date_time")
                                    ));
                                }
                            }

                            CheckRequestStatusByLawyerAdapter adapter = new CheckRequestStatusByLawyerAdapter(requestList,
                                    LawyerRequestActivity.this, 2);
                            lawyer_request_LV.setAdapter(adapter);

                        } catch (JSONException e) {
                            lawyer_request_LV.setAdapter(null);
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LawyerRequestActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}