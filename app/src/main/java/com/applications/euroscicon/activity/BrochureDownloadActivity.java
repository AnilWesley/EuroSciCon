package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.BrochureDownload;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrochureDownloadActivity extends AppCompatActivity {

    @BindView(R.id.spinnerTitle)
    Spinner spinnerTitle;
    @BindView(R.id.editFirst)
    TextInputEditText editFirst;
    @BindView(R.id.editCompany)
    TextInputEditText editCompany;
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;
    @BindView(R.id.editEmail)
    TextInputEditText editEmail;
    @BindView(R.id.editPhone)
    TextInputEditText editPhone;
    @BindView(R.id.editQuires)
    TextInputEditText editQuires;
    @BindView(R.id.btnDownload)
    Button btnDownload;

    String title, firstName, company, country, email, phone, quires;

    String conf_id;
    String actionTitle, shorttitle, conf_type;
    String TAG = "RESPONSE_DATA";
    MyAppPrefsManager myAppPrefsManager;
    String date;
    String path;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure_download);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brochure Download");

        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = intent.getStringExtra("shorttitle");
            conf_type = intent.getStringExtra("conf_type");

        }

        myAppPrefsManager = new MyAppPrefsManager(BrochureDownloadActivity.this);

        editFirst.setText(myAppPrefsManager.getUserName());
        editEmail.setText(myAppPrefsManager.getUserEmail());


        Date c = Calendar.getInstance().getTime();


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(c);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.title, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerTitle.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerCountry.setAdapter(adapter1);


        spinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = spinnerTitle.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = spinnerCountry.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @OnClick(R.id.btnDownload)
    public void onViewClicked() {

        title = spinnerTitle.getSelectedItem().toString();
        firstName = Objects.requireNonNull(editFirst.getText()).toString().trim();
        company = Objects.requireNonNull(editCompany.getText()).toString().trim();
        country = spinnerCountry.getSelectedItem().toString();
        email = Objects.requireNonNull(editEmail.getText()).toString().trim();
        phone = Objects.requireNonNull(editPhone.getText()).toString().trim();
        quires = Objects.requireNonNull(editQuires.getText()).toString().trim();


        if (title.equalsIgnoreCase("Select Title")) {
            Toast.makeText(BrochureDownloadActivity.this, "Select Title", Toast.LENGTH_SHORT).show();

        } else if (firstName.isEmpty()) {
            Toast.makeText(BrochureDownloadActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();


        } else if (company.isEmpty()) {
            Toast.makeText(BrochureDownloadActivity.this, "Enter Company", Toast.LENGTH_SHORT).show();

        } else if (country.equalsIgnoreCase("Select Your Country")) {
            Toast.makeText(BrochureDownloadActivity.this, "Select Your Country", Toast.LENGTH_SHORT).show();

        } else if (email.isEmpty()) {
            Toast.makeText(BrochureDownloadActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

        } else if (phone.isEmpty()) {
            Toast.makeText(BrochureDownloadActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (quires.isEmpty()) {
            Toast.makeText(BrochureDownloadActivity.this, "Enter Queries", Toast.LENGTH_SHORT).show();

        } else {
            downloadBrochure();

        }


    }


    public void downloadBrochure() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_id);
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("name", firstName);
        jsonObject.addProperty("country", country);
        jsonObject.addProperty("company", company);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("queries", quires);
        jsonObject.addProperty("date", date);
        jsonObject.addProperty("source", "android");
        Log.d(TAG, "" + jsonObject);
        apiInterface.processDataBrochureDownload(jsonObject).enqueue(new Callback<BrochureDownload>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<BrochureDownload> call, @NotNull Response<BrochureDownload> response) {


                if (response.isSuccessful()) {
                    BrochureDownload brochureDownload = response.body();
                    assert brochureDownload != null;
                    if (brochureDownload.isStatus()) {
                        List<BrochureDownload.FileBean> fileBeans = brochureDownload.getFile();

                        path = fileBeans.get(0).getBrochure_file();

                        Log.d(TAG, "onResponse: " + path);
                        WebView webView = new WebView(getApplicationContext());
                        webView.loadUrl(path);
                        webView.setDownloadListener(new DownloadListener() {
                            public void onDownloadStart(String url, String userAgent,
                                                        String contentDisposition, String mimetype,
                                                        long contentLength) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(path));
                                startActivity(i);
                            }
                        });
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(BrochureDownloadActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(BrochureDownloadActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<BrochureDownload> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(BrochureDownloadActivity.this, DashBoardActivity.class);
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
        Intent intent = new Intent(BrochureDownloadActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }

}
