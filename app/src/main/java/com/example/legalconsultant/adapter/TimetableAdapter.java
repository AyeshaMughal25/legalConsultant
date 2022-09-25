package com.example.legalconsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legalconsultant.R;
import com.example.legalconsultant.model.Timetable;

import java.util.List;

public class TimetableAdapter extends BaseAdapter {
    List<Timetable> timetablesList;
    Context context;
    LayoutInflater inflater;
    public TimetableAdapter(List<Timetable>timetablesList, Context context){
        this.timetablesList= timetablesList;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return timetablesList.size();
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
        convertView = inflater.inflate(R.layout.view_timetable_, parent, false);
        TextView tv_ttt_start_time,tv_ttt_end_time;
        tv_ttt_start_time=convertView.findViewById(R.id.tv_ttt_start_time);
        tv_ttt_start_time.setText(timetablesList.get(position).getTtt_start_time());
        tv_ttt_end_time=convertView.findViewById(R.id.tv_ttt_end_time);
        tv_ttt_end_time.setText(timetablesList.get(position).getTtt_end_time());

        return convertView;
    }
}
