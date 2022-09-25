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
import com.example.legalconsultant.service.UpdateCourtDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteCourtdetail extends AppCompatActivity {
EditText edit_court_name,edit_court_city,edit_court_status;
Button update;
Court court;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_courtdetail);
        edit_court_name=findViewById(R.id.edit_court_name);
        edit_court_city=findViewById(R.id.edit_court_city);
        edit_court_status=findViewById(R.id.edit_court_status);
        update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourtdetail();
            }
        });

    }
    public void updateCourtdetail(){
        court=new Court();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        UpdateCourtDetail service= RetrofitClient.getClient().create(UpdateCourtDetail.class);
        Call<Court> call = service.UpdateCourtDetail(getIntent().getIntExtra("COURT_ID",0 ),edit_court_name.getText().toString(),
                edit_court_city.getText().toString(),edit_court_status.getText().toString());
        call.enqueue(new Callback<Court>() {
            @Override
            public void onResponse(Call<Court> call, Response<Court> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    court = response.body();
                    if (!court.isError()) {
                        Toast.makeText(UpdateDeleteCourtdetail.this,
                                court.getMessage(), Toast.LENGTH_SHORT).show();
;
                    } else {
                        Toast.makeText(UpdateDeleteCourtdetail.this,
                                court.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Court> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UpdateDeleteCourtdetail.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}