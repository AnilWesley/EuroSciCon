package com.applications.euroscicon.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.SubmitAudioVideo;
import com.applications.euroscicon.utils.ProgressRequestBody;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SubmitAudioActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {


    String app_user_id;
    String conf_id;
    String actionTitle, shorttitle, conf_type;
    String title;
    String name;
    String country;
    String address;
    String email;
    String phone;

    String date;
    private ProgressDialog pDialog;

    @BindView(R.id.chronometerTimer)
    Chronometer chronometerTimer;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.btnStop)
    Button btnStop;

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    @BindView(R.id.title)
    TextView title1;
    @BindView(R.id.firstname)
    TextView firstname;
    @BindView(R.id.email)
    TextView email1;
    @BindView(R.id.country)
    TextView country1;
    @BindView(R.id.phone)
    TextView phone1;
    @BindView(R.id.address)
    TextView address1;
    @BindView(R.id.filepath)
    TextView filepath;
    @BindView(R.id.buttonNext)
    Button buttonNext;


    private static String fileName = null;

    private MediaRecorder recorder = null;

    private MediaPlayer player = null;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_audio);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submit Audio");
        pDialog = new ProgressDialog(this);
        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = intent.getStringExtra("shorttitle");
            conf_type = intent.getStringExtra("conf_type");
            title = intent.getStringExtra("nameTitle");
            name = intent.getStringExtra("firstName");
            email = intent.getStringExtra("email");
            country = intent.getStringExtra("country");
            phone = intent.getStringExtra("phone");
            address = intent.getStringExtra("address");
            date = intent.getStringExtra("date");
            app_user_id = intent.getStringExtra("app_user_id");

            title1.setText("Title : " + title);
            firstname.setText("Name : " + name);
            email1.setText("Email : " + email);
            country1.setText("Country : " + country);
            phone1.setText("Phone : " + phone);
            address1.setText("Address : " + address);

        }


        // Record to the external cache directory for visibility
        fileName = Objects.requireNonNull(SubmitAudioActivity.this.getExternalCacheDir()).getAbsolutePath();
        fileName += "/audiorecordtest.3gp";

        chronometerTimer.setBase(SystemClock.elapsedRealtime());

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filepath.getText().toString().equalsIgnoreCase("No Audio Found")) {
                    Toast.makeText(SubmitAudioActivity.this, "Please Record Audio", Toast.LENGTH_SHORT).show();
                } else {
                    submitAbstract(fileName);
                }

            }
        });
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();

        //starting the chronometer
        chronometerTimer.setBase(SystemClock.elapsedRealtime());
        chronometerTimer.start();

        Toast.makeText(SubmitAudioActivity.this, "Start Recording...", Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("SetTextI18n")
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;


        //starting the chronometer
        chronometerTimer.stop();
        chronometerTimer.setBase(SystemClock.elapsedRealtime());

        Log.d(TAG, "stopRecording: " + fileName);
        //showing the play button
        filepath.setText(fileName);
        Toast.makeText(SubmitAudioActivity.this, "Recording saved successfully.", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btnStart, R.id.btnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                requestStoragePermission();

                break;
            case R.id.btnStop:
                stopRecording();
                btnStop.setEnabled(false);
                btnStop.setAlpha(0.3f);

                btnStart.setEnabled(true);
                btnStart.setAlpha(0.9f);

                break;
        }
    }


    public void submitAbstract(String fileName) {

        showProgress("Uploading media ...");
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        //Create a file object using file path
        File file = new File(fileName);


       /* // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(Uri.parse(path)))),
                        file
                );*/

        // Parsing any Media type file


        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

        ProgressRequestBody fileBody = new ProgressRequestBody(file, "*", SubmitAudioActivity.this);


        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploadfile", file.getName(), fileBody);
        RequestBody conf_id1 = RequestBody.create(MediaType.parse("text/plain"), conf_id);
        RequestBody title1 = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phone1 = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody country1 = RequestBody.create(MediaType.parse("text/plain"), country);
        RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody date1 = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody app_user_id1 = RequestBody.create(MediaType.parse("text/plain"), app_user_id);
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), "audio");
        RequestBody source1 = RequestBody.create(MediaType.parse("text/plain"), "android");

        Call<SubmitAudioVideo> call = apiInterface.processDataSendAudioVideo(fileToUpload, conf_id1, title1, name1, country1, email1,
                phone1, address1, date1, app_user_id1, type1,source1);

        call.enqueue(new Callback<SubmitAudioVideo>() {
            @Override
            public void onResponse(@NotNull Call<SubmitAudioVideo> call, @NotNull Response<SubmitAudioVideo> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    SubmitAudioVideo events = response.body();
                    Log.d(TAG, "onResponse1: " + events.isStatus());
                    Log.d(TAG, "onResponse2: " + events.getFile());
                    String title = "Presentation Submission";
                    String message = "Thank You for submission for Presentation. Please check your email to get the status of your Presentation.Please do check your junk or spam folder if it doesn't arrive in your inbox.";
                    displayAlert(title, message);
                    hideProgress();
                } else {
                    hideProgress();
                    Toast.makeText(SubmitAudioActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitAudioVideo> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + call.toString());
                hideProgress();
            }
        });


    }

    @Override
    public void onProgressUpdate(int percentage) {
        // set current progress

        updateProgress(percentage, "Audio Upload", "Please Wait.....");
    }

    @Override
    public void onError() {
        // do something on error
        Toast.makeText(getApplicationContext(), "Audio Uploading Failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        // do something on upload finished,
        // for example, start next uploading at a queue
        hideProgress();
    }

    public void updateProgress(int val, String title, String msg) {
        pDialog.setTitle(title);
        pDialog.setMessage(msg);
        pDialog.setProgress(val);
    }

    public void showProgress(String str) {
        try {
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100); // Progress Dialog Max Value
            pDialog.setMessage(str);
            if (pDialog.isShowing())
                pDialog.dismiss();
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void hideProgress() {
        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayAlert(@NonNull String alertTitle, @Nullable String message) {


        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.submit_abstract, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
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


            Intent intent = new Intent(SubmitAudioActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            alertDialog.dismiss();

        });


    }


    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startRecording();

                        btnStart.setEnabled(false);
                        btnStart.setAlpha(0.3f);

                        btnStop.setEnabled(true);
                        btnStop.setAlpha(0.9f);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission

                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
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
            Intent intent = new Intent(SubmitAudioActivity.this, SubmitPresentationActivity.class);
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
        Intent intent = new Intent(SubmitAudioActivity.this, SubmitPresentationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }


}