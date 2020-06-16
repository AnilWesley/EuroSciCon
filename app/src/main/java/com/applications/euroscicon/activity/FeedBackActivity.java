package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.BaseResponse;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackActivity extends AppCompatActivity {
    private static final String TAG = "RESPONSE_DATA";
    String conf_id;
    String actionTitle, shorttitle, conf_type;
    MyAppPrefsManager myAppPrefsManager;
    String userID, name, email;
    @BindView(R.id.editFirst)
    TextInputEditText editFirst;
    @BindView(R.id.btnDownload)
    Button btnDownload;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");

        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = getIntent().getStringExtra("shorttitle");
            conf_type = getIntent().getStringExtra("conf_type");

        }
        myAppPrefsManager = new MyAppPrefsManager(FeedBackActivity.this);
        userID = myAppPrefsManager.getUserId();
        name = myAppPrefsManager.getUserName();
        email = myAppPrefsManager.getUserEmail();

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(c);

    }

    @OnClick(R.id.btnDownload)
    public void onViewClicked() {

        if (Objects.requireNonNull(editFirst.getText()).toString().isEmpty()) {
            Toast.makeText(FeedBackActivity.this, "Enter Feedback", Toast.LENGTH_SHORT).show();

        } else {
            feedBack();
        }
    }

    public void feedBack() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("confid", conf_id);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("feedback", Objects.requireNonNull(editFirst.getText()).toString());
        jsonObject.addProperty("created_at", date);
        jsonObject.addProperty("source", "android");
        Log.d(TAG, "" + jsonObject);
        apiInterface.processDataFeedBack(jsonObject).enqueue(new Callback<BaseResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {


                if (response.isSuccessful()) {

                    BaseResponse brochureDownload = response.body();
                    assert brochureDownload != null;
                    if (brochureDownload.isStatus()) {
                        Toast.makeText(FeedBackActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(FeedBackActivity.this, DashBoardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("id", conf_id);
                        intent.putExtra("title", actionTitle);
                        intent.putExtra("shorttitle", shorttitle);
                        intent.putExtra("conf_type", conf_type);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(FeedBackActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(FeedBackActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = new Intent(FeedBackActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }

}