package com.example.legalconsultant.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.legalconsultant.ManageUserProfile;
import com.example.legalconsultant.R;
import com.example.legalconsultant.SearchLawyerByUser;
import com.example.legalconsultant.admin.UpdateDeleteCategoryActivity;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.send_request_to_lawyerbyuser;

import java.util.ArrayList;
import java.util.List;

public class GetlawyerByCatAdapter extends BaseAdapter {
    List<User> usersList;
    Context context;
    LayoutInflater inflater;

    public GetlawyerByCatAdapter(List<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return usersList.size();
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
        convertView = inflater.inflate(R.layout.lawyerwithcategory, parent, false);
        TextView tv_user, tv_mail, tv_cntct, tv_cnic, tv_mngstatus, tv_rating;
        ImageView img;
        tv_user = convertView.findViewById(R.id.tv_mnguser);
        tv_mail = convertView.findViewById(R.id.tv_mngmail);
        tv_cnic = convertView.findViewById(R.id.tv_mngcnic);
        tv_cntct = convertView.findViewById(R.id.tv_mngcontct);
        tv_mngstatus = convertView.findViewById(R.id.tv_mngstatus);
        tv_rating = convertView.findViewById(R.id.tv_rating);
        tv_user.setText(usersList.get(position).getUser_name());
        tv_mail.setText(usersList.get(position).getUser_email());
        tv_cntct.setText(usersList.get(position).getUser_contact());
        tv_cnic.setText(usersList.get(position).getUser_cnic());
        tv_mngstatus.setText(usersList.get(position).getUser_status());
        //tv_rating.setText(Integer.valueOf(usersList.get(position).getLawyer_rating()));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, send_request_to_lawyerbyuser.class)
                        .putExtra("USER_ID", usersList.get(position).getUser_id())
                        .putExtra("USER_NAME", usersList.get(position).getUser_name())
                        .putExtra("USER_EMAIL", usersList.get(position).getUser_email())
                        .putExtra("USER_CNIC", usersList.get(position).getUser_cnic())
                        .putExtra("USER_CONTACT", usersList.get(position).getUser_contact())
                        .putExtra("USER_STATUS", usersList.get(position).getUser_status())
                        .putExtra("USER_IMAGE", usersList.get(position).getUser_image())
                        .putExtra("COURT_NAME", usersList.get(position).getCourt_name())
                        .putExtra("USER_CITY", usersList.get(position).getUser_city())
                        .putExtra("USER_FEE", usersList.get(position).getUser_fees())
                        .putExtra("LAWYER_RATING", usersList.get(position).getLawyer_rating())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return convertView;

    }
}
