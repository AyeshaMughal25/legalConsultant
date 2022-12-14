package com.example.legalconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.legalconsultant.util.TinyDB;

public class UserProfile extends AppCompatActivity {
    ImageButton imgbtn;
    public CardView card1, card2, card3, card4;
    TinyDB tinyDB;
    int getClientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        tinyDB= new TinyDB(this);
        imgbtn = findViewById(R.id.imgbtn);
        card1 = findViewById(R.id.c1);
        card2 = findViewById(R.id.c2);
        card4 = findViewById(R.id.c4);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, SearchLawyerByUser.class);
            //tinyDB.putInt();
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, User_Homepage_myrequest.class);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this, MyProfile_user.class);
                startActivity(intent);
            }
        });

    }
}