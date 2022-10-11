package com.example.legalconsultant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.legalconsultant.ManageLawyerProfile;
import com.example.legalconsultant.R;
import com.example.legalconsultant.model.User;

import java.util.List;

public class ManageLawyerAdapter extends BaseAdapter {
    List<User> usersList;
    Context context;
    LayoutInflater inflater;
    public ManageLawyerAdapter(List<User> usersList, Context context) {
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
        convertView = inflater.inflate(R.layout.tv_lawyer, parent, false);
        TextView tv_user, tv_mail, tv_cntct, tv_cnic,tv_pdf;
        tv_user = convertView.findViewById(R.id.tv_mnguser);
        tv_mail = convertView.findViewById(R.id.tv_mngmail);
        tv_cntct = convertView.findViewById(R.id.tv_mngcontct);
        tv_cnic = convertView.findViewById(R.id.tv_mngcnic);
        tv_pdf = convertView.findViewById(R.id.tv_mngpdf);
        tv_user.setText(usersList.get(position).getUser_name());
        tv_mail.setText(usersList.get(position).getUser_email());
        tv_cntct.setText(usersList.get(position).getUser_contact());
        tv_cnic.setText(usersList.get(position).getUser_cnic());
        tv_pdf.setText(usersList.get(position).getUser_pdf());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ManageLawyerProfile.class)
                        .putExtra("USER_NAME", usersList.get(position).getUser_name())
                        .putExtra("USER_ID", usersList.get(position).getUser_id())
                        .putExtra("USER_EMAIL", usersList.get(position).getUser_email())
                        .putExtra("USER_CNIC", usersList.get(position).getUser_cnic())
                        .putExtra("USER_CONTACT", usersList.get(position).getUser_contact())
                        .putExtra("USER_TYPE", usersList.get(position).getUser_type())
                        .putExtra("USER_STATUS", usersList.get(position).getUser_status())
                        .putExtra("USER_PDF", usersList.get(position).getUser_pdf())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return convertView;
    }
}

