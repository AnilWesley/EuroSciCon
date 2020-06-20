package com.applications.euroscicon.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.SubmitAbstract;
import com.applications.euroscicon.models.Template;
import com.applications.euroscicon.models.TrackName;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitAbstractActivity extends AppCompatActivity {

    @BindView(R.id.spinnerTitle)
    Spinner spinnerTitle;
    @BindView(R.id.editFirst)
    TextInputEditText editFirst;
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;
    @BindView(R.id.editEmail)
    TextInputEditText editEmail;
    @BindView(R.id.editPhone)
    TextInputEditText editPhone;
    @BindView(R.id.spinnerAbstractCategory)
    Spinner spinnerAbstractCategory;
    @BindView(R.id.spinnerTrackName)
    Spinner spinnerTrackName;
    @BindView(R.id.editAddress)
    TextInputEditText editAddress;
    @BindView(R.id.buttonAttach)
    Button buttonAttach;
    @BindView(R.id.buttonNext)
    Button buttonNext;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;

    String TAG = "RESPONSE_DATA";

    String conf_id;
    String actionTitle,shorttitle,conf_type;
    String title;
    String name;
    String country;
    String address;
    String email;
    String phone;
    String category;
    String trackName;

    List<String> trackNameList;
    String date;

    int REQUEST_CODE_DOC = 1;
    String docFilePath;
    @BindView(R.id.txtFileChosen)
    TextView txtFileChosen;
    String path;
    private static final int BUFFER_SIZE = 1024 * 2;
    @BindView(R.id.downloadTemplate)
    TextView downloadTemplate;
    MyAppPrefsManager myAppPrefsManager;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;

    String app_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_abstract);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submit Abstract");


        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = intent.getStringExtra("shorttitle");
            conf_type = intent.getStringExtra("conf_type");

        }
        myAppPrefsManager = new MyAppPrefsManager(SubmitAbstractActivity.this);

        app_user_id = myAppPrefsManager.getUserId();
        editFirst.setText(myAppPrefsManager.getUserName());

        editEmail.setText(myAppPrefsManager.getUserEmail());

        trackNameList = new ArrayList<>();
        trackNameList.add("Select Track Name");

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(c);


        getTrackName();

        buttonAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();

            }
        });
        downloadTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTemplate();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                title = spinnerTitle.getSelectedItem().toString().trim();
                name = Objects.requireNonNull(editFirst.getText()).toString().trim();
                email = Objects.requireNonNull(editEmail.getText()).toString().trim();
                phone = Objects.requireNonNull(editPhone.getText()).toString().trim();
                address = Objects.requireNonNull(editAddress.getText()).toString().trim();
                country = Objects.requireNonNull(spinnerCountry.getSelectedItem()).toString().trim();
                category = Objects.requireNonNull(spinnerAbstractCategory.getSelectedItem()).toString().trim();
                trackName = Objects.requireNonNull(spinnerTrackName.getSelectedItem()).toString().trim();


                if (title.equalsIgnoreCase("Select Title")) {
                    Toast.makeText(SubmitAbstractActivity.this, "Select Title", Toast.LENGTH_SHORT).show();

                } else if (name.isEmpty()) {
                    Toast.makeText(SubmitAbstractActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else if (email.isEmpty()) {
                    Toast.makeText(SubmitAbstractActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

                } else if (phone.isEmpty()) {
                    Toast.makeText(SubmitAbstractActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

                } else if (address.isEmpty()) {
                    Toast.makeText(SubmitAbstractActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();

                } else if (country.equalsIgnoreCase("Select Your Country")) {
                    Toast.makeText(SubmitAbstractActivity.this, "Select Your Country", Toast.LENGTH_SHORT).show();

                } else if (category.equalsIgnoreCase("Select Category")) {
                    Toast.makeText(SubmitAbstractActivity.this, "Select Category", Toast.LENGTH_SHORT).show();

                } else if (trackName.equalsIgnoreCase("Select Track Name")) {
                    Toast.makeText(SubmitAbstractActivity.this, "Select Track Name", Toast.LENGTH_SHORT).show();

                }else if (txtFileChosen.getText().toString().equalsIgnoreCase("No File Chosen")) {
                    Toast.makeText(SubmitAbstractActivity.this, "Select File", Toast.LENGTH_SHORT).show();

                } else {
                    submitAbstract();
                }

            }
        });


    }


    public void getTemplate() {
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_id);
        Log.d(TAG, "" + jsonObject);
        apiInterface.processDataGetTemplate(jsonObject).enqueue(new Callback<Template>() {
            @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
            @Override
            public void onResponse(@NotNull Call<Template> call, @NotNull Response<Template> response) {


                if (response.isSuccessful()) {

                    assert response.body() != null;

                    Template events = response.body();

                    if (events.isStatus()) {


                        List<Template.ResultBean> conferencesBeanList = events.getResult();
                        Log.d(TAG, "onResponse: " + conferencesBeanList.size());

                        String template = conferencesBeanList.get(0).getAbstract_template();

                        Log.d(TAG, "onResponse: " + template);
                        WebView webView = new WebView(getApplicationContext());
                        webView.loadUrl(template);
                        webView.setDownloadListener(new DownloadListener() {
                            public void onDownloadStart(String url, String userAgent,
                                                        String contentDisposition, String mimetype,
                                                        long contentLength) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(template));
                                startActivity(i);
                            }
                        });

                    } else {
                        Toast.makeText(SubmitAbstractActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SubmitAbstractActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Template> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });


    }

    public void getTrackName() {

        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_id);
        Log.d(TAG, "" + jsonObject);
        apiInterface.processDataGetTrackName(jsonObject).enqueue(new Callback<TrackName>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<TrackName> call, @NotNull Response<TrackName> response) {


                if (response.isSuccessful()) {

                    assert response.body() != null;

                    TrackName events = response.body();

                    if (events.isStatus()) {

                        trackNameList.clear();
                        trackNameList.add("Select Track Name");
                        List<TrackName.TracksBean> conferencesBeanList = events.getTracks();
                        Log.d(TAG, "onResponse: " + conferencesBeanList.size());

                        for (TrackName.TracksBean trackName : conferencesBeanList) {
                            String trackName1 = trackName.getTrackName();
                            trackNameList.add(trackName1);
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(SubmitAbstractActivity.this, android.R.layout.simple_spinner_dropdown_item, trackNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerTrackName.setAdapter(adapter);


                    } else {
                        trackNameList.clear();
                        trackNameList.add("Select Track Name");
                        trackNameList.add("No Tracks");
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(SubmitAbstractActivity.this, android.R.layout.simple_spinner_dropdown_item, trackNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerTrackName.setAdapter(adapter);

                    }
                } else {
                    trackNameList.clear();
                    trackNameList.add("Select Track Name");
                    trackNameList.add("No Tracks");
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(SubmitAbstractActivity.this, android.R.layout.simple_spinner_dropdown_item, trackNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerTrackName.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(@NotNull Call<TrackName> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });


    }

    public void submitAbstract() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        //Create a file object using file path
        File file = new File(path);


       /* // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(Uri.parse(path)))),
                        file
                );*/

        // Parsing any Media type file


        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploadfile", file.getName(), requestBody);
        RequestBody conf_id1 = RequestBody.create(MediaType.parse("text/plain"), conf_id);
        RequestBody title1 = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phone1 = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody country1 = RequestBody.create(MediaType.parse("text/plain"), country);
        RequestBody category1 = RequestBody.create(MediaType.parse("text/plain"), category);
        RequestBody trackName1 = RequestBody.create(MediaType.parse("text/plain"), trackName);
        RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody date1 = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody app_user_id1 = RequestBody.create(MediaType.parse("text/plain"), app_user_id);
        RequestBody source1 = RequestBody.create(MediaType.parse("text/plain"), "android");

        Call<SubmitAbstract> call = apiInterface.processDataSendAbstract(fileToUpload, conf_id1, title1, name1, country1, email1,
                phone1, category1, trackName1, address1, date1, app_user_id1, source1);

        call.enqueue(new Callback<SubmitAbstract>() {
            @Override
            public void onResponse(@NotNull Call<SubmitAbstract> call, @NotNull Response<SubmitAbstract> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    SubmitAbstract events = response.body();
                    Log.d(TAG, "onResponse1: " + events.isStatus());
                    Log.d(TAG, "onResponse2: " + events.getFile());
                    String title = "Abstract Submission";
                    String message = "Thank You for submission for Abstract. Please check your email to get the status of your Abstract.Please do check your junk or spam folder if it doesn't arrive in your inbox.";
                    displayAlert(title, message);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SubmitAbstractActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitAbstract> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString());
                progressBar.setVisibility(View.GONE);

            }
        });


    }


    private void displayAlert(@NonNull String alertTitle, @Nullable String message) {


        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.submit_abstract, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView txtTitle = dialogView.findViewById(R.id.txtTitle);
        TextView txtMsg = dialogView.findViewById(R.id.txtMsg);
        ImageView image = dialogView.findViewById(R.id.image);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);


        txtTitle.setText(alertTitle);
        txtMsg.setText(message);
        image.setImageResource(R.drawable.ic_success);
        buttonOk.setOnClickListener(v -> {


            Intent intent = new Intent(SubmitAbstractActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            alertDialog.dismiss();

        });


    }

    /**
     * Alert dialog for capture or select from galley
     */
    private void selectFile() {
        final CharSequence[] items = {"Choose File",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitAbstractActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Choose File")) {
                requestStoragePermission();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            assert uri != null;
            String uriString = uri.toString();
            File myFile = new File(uriString);

            path = getFilePathFromURI(SubmitAbstractActivity.this, uri);
            String fileExt = MimeTypeMap.getFileExtensionFromUrl(uriString);
            Log.d(TAG, "" + path);
            Log.d(TAG, "" + fileExt);
            txtFileChosen.setText(path);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        assert path != null;
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            dispatchFileIntent();

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }


    /**
     * Select image fro gallery
     */
    private void dispatchFileIntent() {
        //checking the permission
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.setType("application/pdf");
        intent.setType("application/*");
        startActivityForResult(intent, 1);
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }


    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SubmitAbstractActivity.this, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", conf_id);
            intent.putExtra("title", actionTitle);
            intent.putExtra("shorttitle", shorttitle);
            intent.putExtra("conf_type", conf_type);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = new Intent(SubmitAbstractActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }
}
