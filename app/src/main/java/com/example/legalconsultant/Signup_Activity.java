package com.example.legalconsultant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.PatternsCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legalconsultant.adapter.CategoryAdapter;
import com.example.legalconsultant.adapter.CourtAdapter;
import com.example.legalconsultant.admin.ManageCourtsActivity;
import com.example.legalconsultant.model.Category;
import com.example.legalconsultant.model.Court;
import com.example.legalconsultant.model.User;
import com.example.legalconsultant.retrofit.RetrofitClient;
import com.example.legalconsultant.service.GetCategoryService;
import com.example.legalconsultant.service.GetCourtService;
import com.example.legalconsultant.service.LawyerRegistrationService;
import com.example.legalconsultant.service.UserRegistrationService;
import com.example.legalconsultant.util.TinyDB;
import com.example.legalconsultant.util.UriUtils;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Signup_Activity extends AppCompatActivity {
    CircleImageView profile_image;
    EditText name, email, password, confirmpsw, Cnic, contact;
    File file;
    Button signup;
    RadioGroup rdgrp;
    RadioButton clbtn, lawbtn;
    LinearLayout LawyerLL;
    private Uri filePath, cameraImageURI, pdfData;
    TextView pdftextview;
    ImageView choosefile;
    boolean isProfileImgClicked = false;
    private final int REQ = 3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE_PHONE = 2;
    boolean isCameraCaptured = false;
    private String pdfName;
    RequestBody requestFileImage, requestFilePDF;
    private static final int REQUEST_DOCUMENT = 3;
    private Uri pdfPath;
    File pdfFile;
    Spinner court_spinner;
    String emailPattern;

    private static final String TAG = "Signup_Activity";
    Spinner category_spinner;
    EditText edt_lawyer_fee, edt_lawyer_city;

    List<Court> courtList = new ArrayList<>();
    List<String> courtNameList = new ArrayList<>();
    int selectedCourtID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        profile_image = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
        password = findViewById(R.id.psw);
        confirmpsw = findViewById(R.id.confirmpsw);
        choosefile = findViewById(R.id.attachfile);
        court_spinner = findViewById(R.id.court_spinner);
        edt_lawyer_city = findViewById(R.id.edt_lawyer_city);
        LawyerLL = findViewById(R.id.LawyerLL);
        edt_lawyer_fee = findViewById(R.id.edt_lawyer_fee);
        Cnic = findViewById(R.id.cnic);
        pdftextview = findViewById(R.id.addpdf);
        contact = findViewById(R.id.mobnum);
        category_spinner = findViewById(R.id.category_spinner);
        rdgrp = findViewById(R.id.radiogroup);
        clbtn = findViewById(R.id.radioclient);
        lawbtn = findViewById(R.id.radiolawyer);
        signup = findViewById(R.id.btn_sign_up);
        //inuitialize validation style


        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPDF();

            }
        });

        lawbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LawyerLL.setVisibility(View.VISIBLE);
                } else {
                    LawyerLL.setVisibility(View.GONE);
                }
            }
        });

        clbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LawyerLL.setVisibility(View.GONE);
                }
            }
        });

        Dexter.withContext(this)
                .withPermissions(
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            profile_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isProfileImgClicked = true;
                                    showImagePickerDialog();
                                }
                            });
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Toast.makeText(Signup_Activity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lawbtn.isChecked()) {
                    if (name.getText().toString().isEmpty()) {
                        name.setError("Field cannot be empty");
                    } else if (name.getText().toString().length() >= 20) {
                        name.setError("username is too long");
                    } else if (email.getText().toString().isEmpty()) {
                        email.setError(" Email cannot be empty");
                    } else if (!isValidEmailId(email.getText().toString().trim())) {
                        email.setError(" invalid email");
                    } else if (password.getText().toString().isEmpty()) {
                        password.setError("password cannot be empty");
                    } else if (confirmpsw.getText().toString().isEmpty()) {
                        confirmpsw.setError("must filled this field for confirmation of password");
                    } else if (!password.getText().toString().trim().equals(confirmpsw.getText().toString().trim())) {
                        confirmpsw.setError("Password do not match");
                    } else if (Cnic.getText().toString().isEmpty()) {
                        Cnic.setError("Cnic feild cannot be empty");
                    } else if (contact.getText().toString().isEmpty()) {
                        contact.setError("feild cannot be empty");
                    } else if (edt_lawyer_fee.getText().toString().isEmpty()) {
                        edt_lawyer_fee.setError("please Enter a fee");
                    } else if (edt_lawyer_city.getText().toString().isEmpty()) {
                        edt_lawyer_city.setError("please Enter a city");
                    } else {
                        Lawyer_reg();
                    }
                } else if (clbtn.isChecked()) {
                    if (name.getText().toString().isEmpty()) {
                        name.setError("Field cannot be empty");
                    } else if (name.getText().toString().length() >= 20) {
                        name.setError("username is too long");
                    } else if (email.getText().toString().isEmpty()) {
                        email.setError(" Email cannot be empty");
                    } else if (!isValidEmailId(email.getText().toString().trim())) {
                        email.setError(" invalid email");
                    } else if (password.getText().toString().isEmpty()) {
                        password.setError("password cannot be empty");
                    } else if (confirmpsw.getText().toString().isEmpty()) {
                        confirmpsw.setError("must filled this field for confirmation of password");
                    } else if (!password.getText().toString().trim().equals(confirmpsw.getText().toString().trim())) {
                        confirmpsw.setError("Password do not match");

                    } else if (Cnic.getText().toString().isEmpty()) {
                        Cnic.setError("Cnic feild cannot be empty");
                    } else if (contact.getText().toString().isEmpty()) {
                        contact.setError("feild cannot be empty");
                    } else {
                        User_reg();
                    }
                }

            }
        });

        GetCategory();
        GetCourts();

    }

    private void loadPDF() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQ);

    }

    private void showImagePickerDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_pic_picker);

        ImageView imgGallery = dialog.findViewById(R.id.img_gallery);
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGalleryImage();
                dialog.dismiss();
            }
        });

        ImageView imageCamera = dialog.findViewById(R.id.img_camera);
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTakePicture();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    /**
     * load gallery function
     */
    private void loadGalleryImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * load camera function
     */
    private void loadTakePicture() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_IMAGE_CAPTURE_PHONE);
        }
    }

    /**
     * Start another activity task
     * and get the data from that activity
     * and use the result of that activity in this activity
     */
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf File"), REQ);
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    if (isProfileImgClicked) {
                        isCameraCaptured = false;
                        if (profile_image != null) {
                            profile_image.setBackground(null);
                        }
                        Uri selectedImage = data.getData();
                        try {
                            isProfileImgClicked = true;
                            filePath = data.getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            profile_image.setImageURI(selectedImage);
                        } catch (IOException e) {
                            Log.i("TAG", "Some exception " + e);
                        }
                    }

                    break;

                case REQUEST_IMAGE_CAPTURE_PHONE:
                    if (isProfileImgClicked) {
                        isCameraCaptured = true;
                        if (profile_image != null) {
                            profile_image.setBackground(null);
                        }
                        if (data != null && data.getExtras() != null) {
                            isProfileImgClicked = true;
                            filePath = data.getData();
                            Bitmap imagebitmap = (Bitmap) data.getExtras().get("data");
                            if (imagebitmap != null) {
                                filePath = getImageUri(imagebitmap);
                                profile_image.setImageBitmap(imagebitmap);
                            }

                        }
                    }
                    break;
                case REQ:
                    Uri uri = data.getData();
                    pdfPath = uri;
                    String displayName = UriUtils.getPathFromUri(Signup_Activity.this, pdfPath);
                    pdftextview.setText(displayName);
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


    public String getCameraRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Get the path if user select the picture from gallery
     * as well as if user click the photo from camera
     * we get the path in both cases
     */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    User user;
    ProgressDialog progressDialog;


    private void Lawyer_reg() {
        user = new User();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("please wait..");
        progressDialog.show();
        new TinyDB(Signup_Activity.this).putString(AppConstants.lawyer_pass, password.getText().toString().trim());

        pdfFile = new File(UriUtils.getPathFromUri(Signup_Activity.this, pdfPath));

        requestFilePDF = RequestBody.create(MediaType.parse("/*"), pdfFile);


        if (filePath == null) {
            Toast.makeText(this, "no image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            if (isCameraCaptured) {
                file = new File(getCameraRealPathFromURI(filePath));
//                file = new File(String.valueOf(filePath));
            } else {
                file = new File(getRealPathFromURI(filePath));
            }
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody _name = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());

        RequestBody _email = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());

        RequestBody _password = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());

        RequestBody cnic = RequestBody.create(MediaType.parse("text/plain"), Cnic.getText().toString());

        RequestBody _contact = RequestBody.create(MediaType.parse("text/plain"), contact.getText().toString());

        RequestBody categoryid = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(selectedCatID));

        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("L"));

        RequestBody user_status = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("P"));

        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), edt_lawyer_city.getText().toString());

        RequestBody fee = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Integer.parseInt(edt_lawyer_fee.getText().toString())));

        RequestBody courtID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(selectedCourtID));


        MultipartBody.Part body = MultipartBody.Part.createFormData("user_image", file.getName(), requestFile);

        MultipartBody.Part pdfbody = MultipartBody.Part.createFormData("user_pdf", pdfFile.getName(), requestFilePDF);

        LawyerRegistrationService service = RetrofitClient.getClient().create(LawyerRegistrationService.class);
        Call<User> call = service.LawyerRegister(
                _name,
                _email, _password,
                _contact, cnic, type, categoryid, pdfbody, user_status, body, city, fee, courtID);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void User_reg() {

        user = new User();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("please wait..");
        progressDialog.show();

        if (filePath == null) {
            Toast.makeText(this, "no image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            if (isCameraCaptured) {
                file = new File(getCameraRealPathFromURI(filePath));
//                file = new File(String.valueOf(filePath));
            } else {
                file = new File(getRealPathFromURI(filePath));
            }
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody _name = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());

        RequestBody _email = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());

        RequestBody _password = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());

        RequestBody cnic = RequestBody.create(MediaType.parse("text/plain"), Cnic.getText().toString());

        RequestBody _contact = RequestBody.create(MediaType.parse("text/plain"), contact.getText().toString());

        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("C"));

        RequestBody user_status = RequestBody.create(MediaType.parse("text/plain"), String.valueOf("A"));

        MultipartBody.Part body = MultipartBody.Part.createFormData("user_image", file.getName(), requestFile);

        UserRegistrationService service = RetrofitClient.getClient().create(UserRegistrationService.class);

        Call<User> call = service.UserRegister(
                _name,
                _email, _password, cnic,
                _contact, type, user_status, body);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    user = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), user.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<Category> categoryList = new ArrayList<>();
    List<String> categoryName = new ArrayList<>();
    int selectedCatID;

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
                                categoryName.add(data.getString("cat_name"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Signup_Activity.this,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    categoryName);
                            category_spinner.setAdapter(adapter);
                            category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedCatID = categoryList.get(position).getCat_id();
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
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void GetCourts() {
        courtList.clear();
        courtNameList.clear();

        GetCourtService service = RetrofitClient.getClient().create(GetCourtService.class);
        Call<JsonObject> call = service.getCourt();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("records");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                courtList.add(new Court(
                                        data.getInt("court_id"),
                                        data.getString("court_name"),
                                        data.getString("court_city"),
                                        data.getString("court_status")
                                ));
                                courtNameList.add(data.getString("court_name"));
                            }

                            ArrayAdapter<String> courtAdapter = new ArrayAdapter<>(Signup_Activity.this,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    courtNameList);
                            court_spinner.setAdapter(courtAdapter);
                            court_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedCourtID = courtList.get(position).getCourt_id();
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
                Toast.makeText(Signup_Activity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}





