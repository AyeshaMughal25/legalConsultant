package com.example.legalconsultant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.legalconsultant.R;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.InsertCourtService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCourtActivity extends AppCompatActivity {

    EditText edt_court_name, edt_court_city;
    Button btn_submit_court;
    Court court;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_court);
        edt_court_name = findViewById(R.id.edt_court_name);
        edt_court_city = findViewById(R.id.edt_court_city);
        btn_submit_court = findViewById(R.id.btn_submit_court);

        btn_submit_court.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCourt();
            }
        });

    }

    private void AddCourt() {
        court = new Court();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        InsertCourtService service = RetrofitClient.getClient().create(InsertCourtService.class);
        Call<Court> call = service.AddCourt(edt_court_name.getText().toString(),
                edt_court_city.getText().toString());
        call.enqueue(new Callback<Court>() {
            @Override
            public void onResponse(Call<Court> call, Response<Court> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    court = response.body();
                    if (!court.isError()) {
                        Toast.makeText(AddCourtActivity.this,
                                court.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCourtActivity.this,
                                court.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<Court> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddCourtActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}