package com.example.legalconsultant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legalconsultant.ApptDetailActivity;
import com.example.legalconsultant.R;
import com.example.legalconsultant.model.Appointment;

import java.util.List;

public class ViewAppoinmentAdaptor extends BaseAdapter {
    List<Appointment> appointmentList;
    Context context;
    int check;
    LayoutInflater inflater;

    public ViewAppoinmentAdaptor(List<Appointment> appointmentList, Context context, int check) {
        this.appointmentList = appointmentList;
        this.context = context;
        this.check = check;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return appointmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.appointment_with_client, parent, false);
        TextView appt_id, appt_title, appt_description, customer_id, appt_time,appt_end_time, appt_date,
                appt_status, appt_date_time,appt_day;

        appt_title = convertView.findViewById(R.id.tv_appt_title);
        appt_description = convertView.findViewById(R.id.tv_appt_description);
        appt_time = convertView.findViewById(R.id.tv_appt_start_time);
        appt_end_time = convertView.findViewById(R.id.tv_appt_End_time);
        appt_day=convertView.findViewById(R.id.tv_appt_day);
        appt_date = convertView.findViewById(R.id.tv_appt_date);
        appt_status = convertView.findViewById(R.id.tv_appt_status);
        appt_date_time = convertView.findViewById(R.id.tv_appt_date_time);

//        appt_id.setText(appointmentList.get(position).getAppt_id());
        appt_title.setText(appointmentList.get(position).getAppt_title());
        appt_description.setText(appointmentList.get(position).getAppt_description());
//customer_id.setText(appointmentList.get(position).getFk_customer_id());
        appt_time.setText(appointmentList.get(position).getAppt_start_time());
        appt_end_time.setText(appointmentList.get(position).getAppt_end_time());
        appt_day.setText(appointmentList.get(position).getAppt_day());
        appt_date.setText(appointmentList.get(position).getAppt_date());
        appt_status.setText(appointmentList.get(position).getAppt_status());
        appt_date_time.setText(appointmentList.get(position).getCreated_Date_Time());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,
                        ApptDetailActivity.class)
                        .putExtra("TITLE", appointmentList.get(position).getAppt_title())
                        .putExtra("APPT_ID", appointmentList.get(position).getAppt_id())
                        .putExtra("DESCRIPTION", appointmentList.get(position).getAppt_description())
                        .putExtra("AGAINST_ID", appointmentList.get(position).getFk_customer_id())
                        .putExtra("REQ_ID", appointmentList.get(position).getFk_request_id())
                        .putExtra("START_TIME", appointmentList.get(position).getAppt_start_time())
                        .putExtra("END_TIME", appointmentList.get(position).getAppt_end_time())
                        .putExtra("DAY", appointmentList.get(position).getAppt_day())
                        .putExtra("DATE", appointmentList.get(position).getAppt_date())
                        .putExtra("STATUS", appointmentList.get(position).getAppt_status())
                        .putExtra("CHECK", check)

                );
            }
        });

        return convertView;
    }
}

