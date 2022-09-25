package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.legalconsultant.lawyer.LawyerRequestActivity;

public class LawyerDashboard extends AppCompatActivity {
    ImageButton imgbtn;
    CardView view_request_CV, card2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_dashboard);
        imgbtn = findViewById(R.id.imgbtn);
        card2 = findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        view_request_CV = findViewById(R.id.view_request_CV);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LawyerDashboard.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        view_request_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LawyerRequestActivity.class));
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(LawyerDashboard.this, My_profile_Lawyer.class);
                                         startActivity(intent);
                                     }
                                 }
        );
c3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
 startActivity(new Intent(getApplicationContext(), TimetableofLawyer.class));
    }
});
    }

}