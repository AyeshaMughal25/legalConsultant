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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.legalconsultant.R;
import com.example.legalconsultant.SearchLawyerByUser;
import com.example.legalconsultant.admin.UpdateDeleteCategoryActivity;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    List<Category> categoryList;
    Context context;
    LayoutInflater inflater;
    Spinner spinner;
    List<Category> categoryLists = new ArrayList<>();

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
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
        convertView = inflater.inflate(R.layout.item_category, parent, false);
        TextView tv_item_category;
        tv_item_category = convertView.findViewById(R.id.tv_item_category);
        tv_item_category.setText(categoryList.get(position).getCat_name());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UpdateDeleteCategoryActivity.class)
                        .putExtra("CAT_ID", categoryList.get(position).getCat_id())
                        .putExtra("CAT_NAME", categoryList.get(position).getCat_name())
                );
            }
        });

        return convertView;
    }
}
