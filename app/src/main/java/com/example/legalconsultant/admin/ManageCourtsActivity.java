package com.example.legalconsultant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.adapter.CourtAdapter;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.GetCourtService;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageCourtsActivity extends AppCompatActivity {

    Button btn_add_court;
    ListView manage_court_LV;
    List<Court> courtList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courts);
        btn_add_court = findViewById(R.id.btn_add_court);
        manage_court_LV = findViewById(R.id.manage_court_LV);

        btn_add_court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddCourtActivity.class));
            }
        });
        GetCourts();
    }

    private void GetCourts() {
        courtList.clear();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        GetCourtService service = RetrofitClient.getClient().create(GetCourtService.class);
        Call<JsonObject> call = service.getCourt();
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

                                courtList.add(new Court(
                                        data.getInt("court_id"),
                                        data.getString("court_name"),
                                        data.getString("court_city"),
                                        data.getString("court_status")
                                ));

                            }

                            CourtAdapter adapter = new CourtAdapter(courtList, ManageCourtsActivity.this);
                            manage_court_LV.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ManageCourtsActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}