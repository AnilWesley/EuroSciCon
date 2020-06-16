package com.applications.euroscicon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.utils.CountryToPhonePrefix;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalInfoActivity extends AppCompatActivity {

    private static final String TAG = "RESPONSE_DATA";


    String id, userName, emailID, mobileNumber, country, countryId, backKey,actionTitle,shorttitle,conf_type;

    int  countryPos;
    String userPrefix;
    @BindView(R.id.editFirst1)
    TextInputEditText editFirst;
    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;
    @BindView(R.id.editEmail1)
    TextInputEditText editEmail;
    @BindView(R.id.editPhone1)
    TextInputEditText editPhone;
    @BindView(R.id.buttonNext)
    Button buttonNext;
    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;


    @BindView(R.id.editPhoneId)
    TextInputEditText editPhoneId;
    CountryToPhonePrefix countryToPhonePrefix;

    MyAppPrefsManager myAppPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Personal Information");

        myAppPrefsManager=new MyAppPrefsManager(PersonalInfoActivity.this);

        countryToPhonePrefix=new CountryToPhonePrefix();

        ArrayList<CountryToPhonePrefix> list = (ArrayList<CountryToPhonePrefix>) countryToPhonePrefix.getAll();
        ArrayAdapter<CountryToPhonePrefix> adapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(adapter);


        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = getIntent().getStringExtra("shorttitle");
            conf_type = getIntent().getStringExtra("conf_type");
        }


        editFirst.setText(myAppPrefsManager.getUserName());
        editEmail.setText(myAppPrefsManager.getUserEmail());


        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = spinnerCountry.getSelectedItem().toString();
                countryPos = spinnerCountry.getSelectedItemPosition();

                CountryToPhonePrefix categories = (CountryToPhonePrefix) parent.getItemAtPosition(position);
                editPhoneId.setText("+" + categories.getPrefix());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = Objects.requireNonNull(editFirst.getText()).toString().trim();
                mobileNumber = Objects.requireNonNull(editPhone.getText()).toString().trim();
                emailID = Objects.requireNonNull(editEmail.getText()).toString().trim();

                if (userName.isEmpty()){
                    Toast.makeText(PersonalInfoActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();

                }
                else if (emailID.isEmpty()) {
                    Toast.makeText(PersonalInfoActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();

                } else if (mobileNumber.isEmpty()){
                    Toast.makeText(PersonalInfoActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();

                }
                else if (country.equalsIgnoreCase("Select Your Country")){
                    Toast.makeText(PersonalInfoActivity.this, "Select Your Country", Toast.LENGTH_SHORT).show();

                }

                else {

                    Intent intent = new Intent(PersonalInfoActivity.this, PaymentActivity1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("title", actionTitle);
                    bundle.putString("userName", userName);
                    bundle.putString("mobileNumber", mobileNumber);
                    bundle.putString("emailID", emailID);
                    bundle.putString("country", country);
                    bundle.putString("shorttitle", shorttitle);
                    bundle.putString("conf_type", conf_type);


                    intent.putExtras(bundle);

                    Log.d(TAG, "onViewClicked: " + bundle);

                    startActivity(intent);
                }
            }
        });


    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(PersonalInfoActivity.this, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", id);
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
        Intent intent = new Intent(PersonalInfoActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", id);
        intent.putExtra("title", actionTitle);
        intent.putExtra("shorttitle", shorttitle);
        intent.putExtra("conf_type", conf_type);
        startActivity(intent);
    }



}
