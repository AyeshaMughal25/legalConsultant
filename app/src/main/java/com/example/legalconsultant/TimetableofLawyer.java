package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TimetableofLawyer extends AppCompatActivity {
    TextView txt_monday, txt_tuesday, txt_wednesday, txt_thursday, txt_friday, txt_saturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetableof_lawyer);
        txt_monday = findViewById(R.id.txt_monday);
        txt_tuesday = findViewById(R.id.txt_tuesday);
        txt_wednesday = findViewById(R.id.txt_wednesday);
        txt_thursday = findViewById(R.id.txt_thursday);
        txt_friday = findViewById(R.id.txt_friday);
        txt_saturday = findViewById(R.id.txt_saturday);
        txt_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Mon);
                startActivity(intent);
            }
        });
        txt_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Tue);
                startActivity(intent);
            }
        });
        txt_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Wed);
                startActivity(intent);
            }
        });
        txt_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Thu);
                startActivity(intent);
            }
        });
        txt_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Fri);
                startActivity(intent);
            }
        });
        txt_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableofLawyer.this, SetTimetable.class);
                intent.putExtra(AppConstants.Day, AppConstants.Sat);
                startActivity(intent);
            }
        });


    }
}