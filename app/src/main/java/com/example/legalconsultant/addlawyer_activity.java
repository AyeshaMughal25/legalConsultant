package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.AddCategoryService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addlawyer_activity extends AppCompatActivity {
    Button button;
    ProgressDialog progressDialog;
    Category category;
    Button btn;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlawyer_activity);
        btn = findViewById(R.id.addlaw);
        editText = findViewById(R.id.edittext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCategory();
            }
        });
    }


    public void AddCategory() {
        category = new Category();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        AddCategoryService service = RetrofitClient.getClient().create(AddCategoryService.class);
        Call<Category> call = service.CheckCategory(editText.getText().toString());
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    category = response.body();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(addlawyer_activity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
