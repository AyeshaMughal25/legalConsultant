package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.GetlawyerByCatAdapter;
import com.example.legalconsultant.adapter.ManageUsersAdapter;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.GetCategoryService;
import com.example.legalconsultant.service.GetLawyerByCat;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLawyerByUser extends AppCompatActivity {

    Spinner spinner;
    Button btn;
    ListView listView;
    TextView textView;
    List<Category> categoryList = new ArrayList<>();
    List<String> categoryNameList = new ArrayList<>();
    ProgressDialog progressDialog;
    int CATID;


    ArrayList<String> arrIds = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lawyer_by_user);
        spinner = (Spinner) findViewById(R.id.spinner);
        btn = (Button) findViewById(R.id.show_All);
        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.search_lawyer_LV);
        GetCategory();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showlawyerdetail(CATID);
            }
        });
    }

    private void showlawyerdetail(int catID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        GetLawyerByCat service = RetrofitClient.getClient().create(GetLawyerByCat.class);
        Call<JsonObject> call = service.getlawyercategory(catID);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                if (data.getString("user_status").equals("A")) {
                                    userList.add(new User(
                                            data.getInt("user_id"),
                                            data.getString("user_name"),
                                            data.getString("user_email"),
                                            data.getString("user_cnic"),
                                            data.getString("user_contact"),
                                            data.getString("user_status"),
                                            data.getString("user_image"),
                                            data.getString("court_name"),
                                            data.getString("user_city"),
                                            data.getInt("user_fees"),
                                            data.getInt("lawyer_rating")
                                    ));
                                }

                            }

                            GetlawyerByCatAdapter adapter = new GetlawyerByCatAdapter(userList, SearchLawyerByUser.this);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchLawyerByUser.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetCategory() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        GetCategoryService service = RetrofitClient.getClient().create(GetCategoryService.class);
        Call<JsonObject> call = service.getCategories();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                categoryList.add(new Category(
                                        data.getInt("cat_id"),
                                        data.getString("cat_name")
                                ));
                                categoryNameList.add(data.getString("cat_name"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchLawyerByUser.this,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    categoryNameList);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CATID = categoryList.get(position).getCat_id();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchLawyerByUser.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}



