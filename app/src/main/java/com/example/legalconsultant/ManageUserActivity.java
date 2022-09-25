package com.example.legalconsultant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.legalconsultant.adapter.ManageUsersAdapter;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.ManageUser;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUserActivity extends AppCompatActivity {
    ListView view_cat_LV;
    Button btn;
    List<User> userList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        view_cat_LV = findViewById(R.id.view_cat_LV);
        getusers();
    }

    private void getusers() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        ManageUser service = RetrofitClient.getClient().create(ManageUser.class);
        Call<JsonObject> call = service.getusers();
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
                                if (data.getString("user_type").equals("C")) {
                                    userList.add(new User(
                                            data.getInt("user_id"),
                                            data.getString("user_name"),
                                            data.getString("user_email"),
                                            data.getString("user_cnic"),
                                            data.getString("user_contact"),
                                            data.getString("user_type"),
                                            data.getString("user_pdf"),
                                            data.getString("user_status"),
                                            data.getString("user_image")
                                    ));
                                }

                            }

                            ManageUsersAdapter adapter = new ManageUsersAdapter(userList, ManageUserActivity.this);
                            view_cat_LV.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ManageUserActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),
                Admin_Dashboard.class));
        finishAffinity();
    }
}