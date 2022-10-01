package com.example.legalconsultant;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.legalconsultant.model.Request;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.SendRequestService;
import com.example.legalconsultant.util.EndPoint;
import com.example.legalconsultant.util.TinyDB;
import com.example.legalconsultant.util.UriUtils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class send_request_to_lawyerbyuser extends AppCompatActivity {

    int userID, Fee,rating;
    String name, email, cnic, contact, status, image, CourtName, CityName;
    CircleImageView selected_lawyer_image;
    TextView tv_selected_lawyer_name, tv_selected_lawyer_contact, tv_selected_lawyer_cnic,
            tv_selected_lawyer_email, choose_pdf, tv_selected_lawyer_courtname, tv_selected_lawyer_city,
            tv_selected_lawyer_fee,Tv_selected_lawyer_rating;
    Button btn_send_Req;
    File file;
    private Uri pdfData;
    private final int REQ = 1;
    RequestBody requestFilePDF;
    private Uri pdfPath;
    File pdfFile;
    private String pdfName;
    TinyDB tinyDB;
    ProgressDialog progressDialog;
    Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request_to_lawyerbyuser);
        tinyDB = new TinyDB(this);
        userID = getIntent().getIntExtra("USER_ID", 0);
        Fee = getIntent().getIntExtra("USER_FEE", 0);
        rating = getIntent().getIntExtra("RATING",0);
        name = getIntent().getStringExtra("USER_NAME");
        CourtName = getIntent().getStringExtra("COURT_NAME");
        CityName = getIntent().getStringExtra("USER_CITY");
        email = getIntent().getStringExtra("USER_EMAIL");
        cnic = getIntent().getStringExtra("USER_CNIC");
        contact = getIntent().getStringExtra("USER_CONTACT");
        status = getIntent().getStringExtra("USER_STATUS");
        image = getIntent().getStringExtra("USER_IMAGE");
        tv_selected_lawyer_courtname = findViewById(R.id.tv_selected_lawyer_courtname);
        Tv_selected_lawyer_rating = findViewById(R.id.tv_lawyer_rating);
        selected_lawyer_image = findViewById(R.id.selected_lawyer_image);
        tv_selected_lawyer_name = findViewById(R.id.tv_selected_lawyer_name);
        tv_selected_lawyer_contact = findViewById(R.id.tv_selected_lawyer_contact);
        btn_send_Req = findViewById(R.id.btn_send_Req);
        tv_selected_lawyer_cnic = findViewById(R.id.tv_selected_lawyer_cnic);
        tv_selected_lawyer_email = findViewById(R.id.tv_selected_lawyer_email);
        choose_pdf = findViewById(R.id.choose_pdf);
        btn_send_Req = findViewById(R.id.btn_send_Req);
        tv_selected_lawyer_fee = findViewById(R.id.tv_selected_lawyer_fee);
        tv_selected_lawyer_city = findViewById(R.id.tv_selected_lawyer_city);

        Glide.with(this).load(EndPoint.IMAGE_URL + image).into(selected_lawyer_image);
        tv_selected_lawyer_name.setText(name);
        tv_selected_lawyer_contact.setText(contact);
        tv_selected_lawyer_cnic.setText(cnic);
        tv_selected_lawyer_email.setText(email);
        tv_selected_lawyer_city.setText(CityName);
        tv_selected_lawyer_courtname.setText(CourtName);
        tv_selected_lawyer_fee.setText(String.valueOf(Fee));
        Tv_selected_lawyer_rating.setText(String.valueOf(rating));

        choose_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPDF();
            }
        });
        btn_send_Req.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                SendRequest();
            }
        });
    }

    private void loadPDF() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQ);

    }

    private void SendRequest() {
        request = new Request();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();

        pdfFile = new File(UriUtils.getPathFromUri(send_request_to_lawyerbyuser.this, pdfPath));

        requestFilePDF = RequestBody.create(MediaType.parse("/*"), pdfFile);

        RequestBody LawyerID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userID));

        RequestBody ClientID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tinyDB.getInt("CLIENT_ID")));

        MultipartBody.Part body = MultipartBody.Part.createFormData("pdf", pdfFile.getName(), requestFilePDF);

        SendRequestService service = RetrofitClient.getClient().create(SendRequestService.class);

        Call<Request> call = service.SendRequest(
                LawyerID,
                ClientID,
                body
                );

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    request = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), request.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), request.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            pdfPath = uri;
            String displayName = UriUtils.getPathFromUri(send_request_to_lawyerbyuser.this, pdfPath);
            choose_pdf.setText(displayName);
        }
    }

    public String getFileName(Context context, Uri uri) {
        if (uri != null && context != null) {
            Cursor returnCursor =
                    context.getContentResolver().query(uri, null, null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                if (nameIndex >= 0 && sizeIndex >= 0) {
                    Boolean isValidFile = checkOtherFileType(returnCursor.getString(nameIndex));

                    if (!isValidFile) {
                        return returnCursor.getString(nameIndex);
                    }
                }

            }
        }
        return null;
    }

    private Boolean checkOtherFileType(String filePath) {
        if (!filePath.isEmpty()) {
            String filePathInLowerCase = filePath.toLowerCase();
            if (filePathInLowerCase.endsWith(".pdf")) {
                return true;
            }
        }
        return false;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

}