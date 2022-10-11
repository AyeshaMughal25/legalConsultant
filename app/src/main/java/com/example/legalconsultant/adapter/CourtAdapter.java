package com.example.legalconsultant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legalconsultant.R;
import com.example.legalconsultant.admin.UpdateDeleteCourtdetail;
import com.example.legalconsultant.model.Court;

import java.util.ArrayList;
import java.util.List;

public class CourtAdapter extends BaseAdapter {

    List<Court> courtList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CourtAdapter(List<Court> courtList, Context context) {
        this.courtList = courtList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courtList.size();
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
        convertView = inflater.inflate(R.layout.item_court, parent, false);
        TextView tv_item_court_name = convertView.findViewById(R.id.tv_item_court_name);
        TextView tv_item_court_city = convertView.findViewById(R.id.tv_item_court_city);
        TextView tv_item_court_status = convertView.findViewById(R.id.tv_item_court_status);
        tv_item_court_status.setText(courtList.get(position).getCourt_status());
        tv_item_court_city.setText(courtList.get(position).getCourt_city());
        tv_item_court_name.setText(courtList.get(position).getCourt_name());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UpdateDeleteCourtdetail.class)
                        .putExtra("COURT_ID", courtList.get(position).getCourt_id())
                        .putExtra("COURT_NAME", courtList.get(position).getCourt_name())
                        .putExtra("COURT_CITY", courtList.get(position).getCourt_city())
                        .putExtra("COURT_STATUS", courtList.get(position).getCourt_status())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });



        return convertView;
    }
}
