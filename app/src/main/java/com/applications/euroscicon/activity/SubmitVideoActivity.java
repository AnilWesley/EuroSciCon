package com.applications.euroscicon.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import org.jetbrains.annotations.NotNull;

import java.io.File;
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

public class SubmitVideoActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    SimpleExoPlayer absPlayerInternal;
    private ProgressDialog pDialog;
    private static final String TAG = "VIDEO FRAGMENT";
    private static final int REQUEST_VIDEO_CAPTURE = 300;
    @BindView(R.id.pv_main)
    PlayerView pvMain;
    private Uri uri;
    private String pathToStoredVideo;


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
    @BindView(R.id.capture_video)
    Button captureVideo;
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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_video);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submit Video");
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


        captureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestStoragePermission();


            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filepath.getText().toString().equalsIgnoreCase("No Video Found")) {
                    Toast.makeText(SubmitVideoActivity.this, "Please Record Video", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onClick: " + pathToStoredVideo);
                    uploadVideoToServer(pathToStoredVideo);
                }

            }
        });
    }


    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            Intent videoCaptureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            if (videoCaptureIntent.resolveActivity(SubmitVideoActivity.this.getPackageManager()) != null) {
                                startActivityForResult(videoCaptureIntent, REQUEST_VIDEO_CAPTURE);
                            }
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
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_CAPTURE) {
            uri = data.getData();

            TrackSelector trackSelectorDef = new DefaultTrackSelector();
            absPlayerInternal = ExoPlayerFactory.newSimpleInstance(SubmitVideoActivity.this, trackSelectorDef); //creating a player instance

            String userAgent = Util.getUserAgent(SubmitVideoActivity.this, getResources().getString(R.string.app_name));
            DefaultDataSourceFactory defdataSourceFactory = new DefaultDataSourceFactory(SubmitVideoActivity.this, userAgent);
            Uri uriOfContentUrl = Uri.parse(String.valueOf(uri));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(defdataSourceFactory).createMediaSource(uriOfContentUrl);  // creating a media source

            absPlayerInternal.prepare(mediaSource);
            absPlayerInternal.setPlayWhenReady(true); // start loading video and play it at the moment a chunk of it is available offline

            pvMain.setPlayer(absPlayerInternal); // attach surface to the view

            pathToStoredVideo = getRealPathFromURIPath(uri, SubmitVideoActivity.this);
            filepath.setText(pathToStoredVideo);


            //Store the video to your server
            //uploadVideoToServer(pathToStoredVideo);
        }
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitVideoActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private String getFileDestinationPath() {
        String generatedFilename = String.valueOf(System.currentTimeMillis());
        String filePathEnvironment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File directoryFolder = new File(filePathEnvironment + "/video/");
        if (!directoryFolder.exists()) {
            directoryFolder.mkdir();
        }
        //Log.d(TAG, "Full path " + filePathEnvironment + "/video/" + generatedFilename + ".mp4");
        return filePathEnvironment + "/video/" + generatedFilename + ".mp4";
    }


    private void uploadVideoToServer(String pathToVideoFile) {


        showProgress("Uploading media ...");
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);

        File videoFile = new File(pathToVideoFile);
       /* RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("video", videoFile.getName(), videoBody);*/

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), videoFile);

        ProgressRequestBody fileBody = new ProgressRequestBody(videoFile, "*", SubmitVideoActivity.this);


        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploadfile", videoFile.getName(), fileBody);

        RequestBody conf_id1 = RequestBody.create(MediaType.parse("text/plain"), conf_id);
        RequestBody title1 = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody phone1 = RequestBody.create(MediaType.parse("text/plain"), phone);
        RequestBody country1 = RequestBody.create(MediaType.parse("text/plain"), country);
        RequestBody address1 = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody date1 = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody app_user_id1 = RequestBody.create(MediaType.parse("text/plain"), app_user_id);
        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), "video");
        RequestBody source1 = RequestBody.create(MediaType.parse("text/plain"), "android");


        Call<SubmitAudioVideo> serverCom = apiInterface.processDataSendAudioVideo(fileToUpload, conf_id1, title1, name1, country1, email1,
                phone1, address1, date1, app_user_id1, type1,source1);
        serverCom.enqueue(new Callback<SubmitAudioVideo>() {
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
                    Toast.makeText(SubmitVideoActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitAudioVideo> call, @NotNull Throwable t) {
                Log.d(TAG, "Error message " + t.getMessage());

                hideProgress();
            }
        });
    }

    @Override
    public void onProgressUpdate(int percentage) {
        // set current progress

        updateProgress(percentage, "Video Upload", "Please Wait.....");
    }

    @Override
    public void onError() {
        // do something on error
        Toast.makeText(getApplicationContext(), "Video Uploading Failed.", Toast.LENGTH_SHORT).show();
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

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        @SuppressLint("Recycle")
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
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


            Intent intent = new Intent(SubmitVideoActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            alertDialog.dismiss();

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SubmitVideoActivity.this, SubmitPresentationActivity.class);
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
        Intent intent = new Intent(SubmitVideoActivity.this, SubmitPresentationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }


}