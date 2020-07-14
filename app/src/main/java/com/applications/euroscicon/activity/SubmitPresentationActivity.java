package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubmitPresentationActivity extends AppCompatActivity {


    String TAG = "RESPONSE_DATA";

    String conf_id;
    String actionTitle, shorttitle, conf_type;
    String title;
    String name;
    String country;
    String address;
    String email;
    String phone;

    String date;

    int REQUEST_CODE_DOC = 1;
    String docFilePath;
    @BindView(R.id.txtFileChosen)
    TextView txtFileChosen;
    String path;
    private static final int BUFFER_SIZE = 1024 * 2;

    MyAppPrefsManager myAppPrefsManager;

    String app_user_id;
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
    @BindView(R.id.editAddress)
    TextInputEditText editAddress;
    @BindView(R.id.buttonAttach)
    Button buttonAttach;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.buttonRecord)
    Button buttonRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_presentation);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submit Presentation");


        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = intent.getStringExtra("shorttitle");
            conf_type = intent.getStringExtra("conf_type");

        }
        myAppPrefsManager = new MyAppPrefsManager(SubmitPresentationActivity.this);

        app_user_id = myAppPrefsManager.getUserId();
        editFirst.setText(myAppPrefsManager.getUserName());

        editEmail.setText(myAppPrefsManager.getUserEmail());


        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(c);


        buttonAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();

            }
        });

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = spinnerTitle.getSelectedItem().toString().trim();
                name = Objects.requireNonNull(editFirst.getText()).toString().trim();
                email = Objects.requireNonNull(editEmail.getText()).toString().trim();
                phone = Objects.requireNonNull(editPhone.getText()).toString().trim();
                address = Objects.requireNonNull(editAddress.getText()).toString().trim();
                country = Objects.requireNonNull(spinnerCountry.getSelectedItem()).toString().trim();


                if (title.equalsIgnoreCase("Select Title")) {
                    Toast.makeText(SubmitPresentationActivity.this, "Select Title", Toast.LENGTH_SHORT).show();

                } else if (name.isEmpty()) {
                    Toast.makeText(SubmitPresentationActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else if (country.equalsIgnoreCase("Select Country")) {
                    Toast.makeText(SubmitPresentationActivity.this, "Select Country", Toast.LENGTH_SHORT).show();

                }else if (email.isEmpty()) {
                    Toast.makeText(SubmitPresentationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

                } else if (phone.isEmpty()) {
                    Toast.makeText(SubmitPresentationActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

                } else if (address.isEmpty()) {
                    Toast.makeText(SubmitPresentationActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();

                }  else if (txtFileChosen.getText().toString().equalsIgnoreCase("No Type")) {
                    Toast.makeText(SubmitPresentationActivity.this, "Select Type", Toast.LENGTH_SHORT).show();

                } else {

                    if (txtFileChosen.getText().toString().equalsIgnoreCase("Audio")) {
                        //submitAbstract();
                        Intent intent = new Intent(SubmitPresentationActivity.this, SubmitAudioActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id", conf_id);
                        intent.putExtra("title", actionTitle);
                        intent.putExtra("shorttitle", shorttitle);
                        intent.putExtra("conf_type", conf_type);
                        intent.putExtra("nameTitle", title);
                        intent.putExtra("firstName", name);
                        intent.putExtra("email", email);
                        intent.putExtra("country", country);
                        intent.putExtra("phone", phone);
                        intent.putExtra("address", address);
                        intent.putExtra("date", date);
                        intent.putExtra("app_user_id", app_user_id);
                        startActivity(intent);
                    } else if (txtFileChosen.getText().toString().equalsIgnoreCase("Video")) {
                        //submitAbstract();
                        Intent intent = new Intent(SubmitPresentationActivity.this, SubmitVideoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id", conf_id);
                        intent.putExtra("title", actionTitle);
                        intent.putExtra("shorttitle", shorttitle);
                        intent.putExtra("conf_type", conf_type);
                        intent.putExtra("nameTitle", title);
                        intent.putExtra("firstName", name);
                        intent.putExtra("email", email);
                        intent.putExtra("country", country);
                        intent.putExtra("phone", phone);
                        intent.putExtra("address", address);
                        intent.putExtra("date", date);
                        intent.putExtra("app_user_id", app_user_id);
                        startActivity(intent);
                    }
                }

            }
        });


    }


    /**
     * Alert dialog for capture or select from galley
     */
    @SuppressLint("SetTextI18n")
    private void selectFile() {
        final CharSequence[] items = {"Record Audio",
                "Record Video"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitPresentationActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Record Audio")) {

                txtFileChosen.setText("Audio");
                buttonRecord.setText("Record Audio");


            } else if (items[item].equals("Record Video")) {
                txtFileChosen.setText("Video");
                buttonRecord.setText("Record Video");
            }
        });
        builder.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SubmitPresentationActivity.this, DashBoardActivity.class);
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
        Intent intent = new Intent(SubmitPresentationActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", conf_id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }
}
