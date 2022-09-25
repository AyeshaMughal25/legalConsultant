package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.legalconsultant.admin.ManageCourtsActivity;
import com.example.legalconsultant.admin.ManageLawyerActivity;

public class Admin_Dashboard extends AppCompatActivity {
    public CardView card1, card2, card3, card4, card5, c6;
    public ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__dashboard);
        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        c6 = (CardView) findViewById(R.id.c6);
        imgbtn = findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, addlawyerbyadmin_activity.class);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, ManageUserActivity.class);
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, ManageLawyerActivity.class);
                startActivity(intent);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent =new Intent(Admin_Dashboard.this, ManageCourtsActivity.class);
                startActivity(intent);
            }
        });

    }


}
