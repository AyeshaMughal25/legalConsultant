package com.example.legalconsultant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legalconsultant.R;
import com.example.legalconsultant.UserRequestDetailActivity;
import com.example.legalconsultant.lawyer.LawyerRequestActivity;
import com.example.legalconsultant.lawyer.LawyerRequestDetailActivity;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.model.User;

import java.util.List;

public class CheckRequestStatusByLawyerAdapter extends BaseAdapter {

    List<Request> requestList;
    Context context;
    int check;
    LayoutInflater inflater;

    public CheckRequestStatusByLawyerAdapter(List<Request> requestList, Context context, int check) {
        this.requestList = requestList;
        this.context = context;
        this.check = check;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return requestList.size();
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
        convertView = inflater.inflate(R.layout.user_request_status, parent, false);
        TextView tv_name, tv_cntct, tv_cnic, tv_mngstatus, pdf, tv_date_time;
        tv_name = convertView.findViewById(R.id.tv_mnguser);
        tv_cnic = convertView.findViewById(R.id.tv_mnguser_cnic);
        tv_cntct = convertView.findViewById(R.id.tv_mnguser_contact);
        tv_name.setText(requestList.get(position).getUser_name());
        tv_cnic.setText(requestList.get(position).getUser_cnic());
        tv_cntct.setText(requestList.get(position).getUser_contact());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, LawyerRequestDetailActivity.class)
                        .putExtra("REQUEST_ID", requestList.get(position).getReq_id())
                        .putExtra("USER_NAME", requestList.get(position).getUser_name())
                        .putExtra("USER_CNIC", requestList.get(position).getUser_cnic())
                        .putExtra("USER_CONTACT", requestList.get(position).getUser_contact())
                        .putExtra("FK_CLIENT_ID", requestList.get(position).getFk_client_id())
                        .putExtra("PDF", requestList.get(position).getPdf())
                        .putExtra("STATUS", requestList.get(position).getStatus())
                        .putExtra("CHECK", check)
                );

            }
        });


        return convertView;
    }

}
