package com.example.legalconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.legalconsultant.util.TinyDB;

public class ViewLawyerTimetable extends AppCompatActivity {
    TextView txt_monday, txt_tuesday, txt_wednesday, txt_thursday, txt_friday, txt_saturday;
    TinyDB tinyDB;
    int getRequestID, getFkLawyerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lawyer_timetable);
        tinyDB = new TinyDB(this);
        txt_monday = findViewById(R.id.txtvw_monday);
        txt_tuesday = findViewById(R.id.txtvw_tuesday);
        txt_wednesday = findViewById(R.id.txtvw_wednesday);
        txt_thursday = findViewById(R.id.txtvw_thursday);
        txt_friday = findViewById(R.id.txtvw_friday);
        txt_saturday = findViewById(R.id.txtvw_saturday);
        getRequestID = getIntent().getIntExtra("REQUEST_ID", 0);
        getFkLawyerID = getIntent().getIntExtra("LAWYER_ID", 0);

        txt_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Mon);

                startActivity(intent);
            }
        });
        txt_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Tue);
                startActivity(intent);
            }
        });
        txt_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Wed);
                startActivity(intent);
            }
        });
        txt_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Thu);
                startActivity(intent);
            }
        });
        txt_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Fri);
                startActivity(intent);
            }
        });
        txt_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewLawyerTimetable.this, showAppointment.class);
                tinyDB.putInt("LAWYER_ID", getFkLawyerID);
                intent.putExtra("LAWYER_ID", getFkLawyerID);
                intent.putExtra(AppConstants.Day, AppConstants.Sat);
                startActivity(intent);
            }
        });


    }


}