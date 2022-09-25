package com.example.legalconsultant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.adapter.CategoryAdapter;
import com.example.legalconsultant.addlawyer_activity;
import com.example.legalconsultant.addlawyerbyadmin_activity;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.AddCategoryService;
import com.example.legalconsultant.service.DeleteCategory;
import com.example.legalconsultant.service.UpdateCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteCategoryActivity extends AppCompatActivity {

    Button updatebtn, deletebtn;
    EditText edittext;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_category);
        updatebtn = findViewById(R.id.update);
        deletebtn = findViewById(R.id.delete);
        edittext = findViewById(R.id.edittext1);
        edittext.setText(getIntent().getStringExtra("CAT_NAME"));
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatecategory();
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletecategory();
            }
        });

    }

    public void updatecategory() {
        category = new Category();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        UpdateCategory service = RetrofitClient.getClient().create(UpdateCategory.class);
        Call<Category> call = service.UpdateCategory(getIntent().getIntExtra("CAT_ID", 0),
                edittext.getText().toString());
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    category = response.body();
                    if (!category.isError()) {
                        Toast.makeText(UpdateDeleteCategoryActivity.this,
                                category.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateDeleteCategoryActivity.this,
                                category.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDeleteCategoryActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletecategory() {
        category = new Category();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        DeleteCategory service = RetrofitClient.getClient().create(DeleteCategory.class);
        Call<Category> call = service.DeleteCategory(getIntent().getIntExtra("CAT_ID", 0));
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    category = response.body();
                    if (!category.isError()) {
                        Toast.makeText(UpdateDeleteCategoryActivity.this,
                                category.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateDeleteCategoryActivity.this,
                                category.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDeleteCategoryActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
