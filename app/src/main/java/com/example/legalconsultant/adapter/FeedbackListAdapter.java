package com.example.legalconsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legalconsultant.FeedBackListActivity;
import com.example.legalconsultant.R;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.model.FeedBack;

import java.util.List;

public class FeedbackListAdapter extends BaseAdapter {
    List<FeedBack> FeedBackList;
    Context context;
    LayoutInflater inflater;
    public FeedbackListAdapter(List<FeedBack> FeedBackList, Context context) {
        this.FeedBackList = FeedBackList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return FeedBackList.size();
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
        convertView=inflater.inflate(R.layout.feedbackdetail,parent,false);
        TextView tv_subj,tv_feedback,tv_rating;
        tv_subj = convertView.findViewById(R.id.tv_subj);
        tv_feedback = convertView.findViewById(R.id.tv_feedback);
        tv_rating = convertView.findViewById(R.id.tv_rating);
        tv_subj.setText(FeedBackList.get(position).getCase_subject());
        tv_feedback.setText(FeedBackList.get(position).getFeedback());
        tv_rating.setText(String.valueOf(FeedBackList.get(position).getRating()));

        return convertView;
    }
}
