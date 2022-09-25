package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.CategoryAdapter;
import com.example.legalconsultant.admin.UpdateDeleteCategoryActivity;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.AddCategoryService;
import com.example.legalconsultant.service.GetCategoryService;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class addlawyerbyadmin_activity extends AppCompatActivity {

    ListView view_cat_LV;
    Button btn;
    List<Category> categoryList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlawyerbyadmin_activity);
        view_cat_LV = findViewById(R.id.view_cat_LV);
        btn = findViewById(R.id.add_lawyer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addlawyerbyadmin_activity.this, addlawyer_activity.class);
                startActivity(intent);
            }
        });
        GetCategory();
    }

    private void GetCategory() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        GetCategoryService service = RetrofitClient.getClient().create(GetCategoryService.class);
        Call<JsonObject> call = service.getCategories();
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
                                categoryList.add(new Category(
                                        data.getInt("cat_id"),
                                        data.getString("cat_name")
                                ));
                            }

                            CategoryAdapter adapter = new CategoryAdapter(categoryList, addlawyerbyadmin_activity.this);
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
                Toast.makeText(addlawyerbyadmin_activity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}