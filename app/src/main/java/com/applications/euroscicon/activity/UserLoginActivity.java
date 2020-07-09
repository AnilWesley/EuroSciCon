package com.applications.euroscicon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.UserLogin;
import com.applications.euroscicon.utils.ConstantValues;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    @BindView(R.id.editEmail)
    TextInputEditText editEmail;
    @BindView(R.id.editPassword)
    TextInputEditText editPassword;
    @BindView(R.id.buttonNext)
    Button buttonNext;
    @BindView(R.id.txtSignin)
    TextView txtSignin;


    String userId, userEmail, userPassword, category, id, title,shorttitle,conf_type;
    String TAG = "RESPONSE_DATA";
    MyAppPrefsManager myAppPrefsManager;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        shorttitle = intent.getStringExtra("shorttitle");
        conf_type = intent.getStringExtra("conf_type");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Login");
        myAppPrefsManager = new MyAppPrefsManager(UserLoginActivity.this);
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("category", category);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("shorttitle", shorttitle);
                intent.putExtra("conf_type", conf_type);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.buttonNext)
    public void onViewClicked() {
        userEmail = Objects.requireNonNull(editEmail.getText()).toString().trim();
        userPassword = Objects.requireNonNull(editPassword.getText()).toString().trim();

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();

        } else if (userPassword.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();

        } else {
            next();
        }

    }

    public void next() {
        progressBar.setVisibility(View.VISIBLE);


        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();


        jsonObject.addProperty("email", userEmail);
        jsonObject.addProperty("password", userPassword);


        Log.d(TAG, "" + jsonObject);

        apiInterface.processDataAppUser(jsonObject).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(@NotNull Call<UserLogin> call, @NotNull Response<UserLogin> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    assert response.body() != null;

                    UserLogin events = response.body();

                    if (events.isStatus()) {

                        List<UserLogin.AppUserBean> appUserIdBeans = events.getApp_user();
                        myAppPrefsManager.setUserLoggedIn(true);
                        myAppPrefsManager.setUserName(appUserIdBeans.get(0).getName());
                        myAppPrefsManager.setUserEmail(appUserIdBeans.get(0).getEmail());
                        myAppPrefsManager.setUserId(appUserIdBeans.get(0).getApp_user_id());

                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
                        if (category.equalsIgnoreCase("register")) {
                            Intent intent = new Intent(UserLoginActivity.this, PersonalInfoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            intent.putExtra("shorttitle", shorttitle);
                            intent.putExtra("conf_type", conf_type);
                            startActivity(intent);
                        } else if (category.equalsIgnoreCase("abstract")) {
                            Intent intent = new Intent(UserLoginActivity.this, SubmitAbstractActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            intent.putExtra("shorttitle", shorttitle);
                            intent.putExtra("conf_type", conf_type);
                            startActivity(intent);
                        } else if (category.equalsIgnoreCase("brochure")) {
                            Intent intent = new Intent(UserLoginActivity.this, BrochureDownloadActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            intent.putExtra("shorttitle", shorttitle);
                            intent.putExtra("conf_type", conf_type);
                            startActivity(intent);
                        }else if (category.equalsIgnoreCase("feedback")) {
                            Intent intent = new Intent(UserLoginActivity.this, FeedBackActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("id", id);
                            intent.putExtra("title", title);
                            intent.putExtra("shorttitle", shorttitle);
                            intent.putExtra("conf_type", conf_type);
                            startActivity(intent);
                        }
                        else if (category.equalsIgnoreCase("history")) {
                            Intent intent = new Intent(UserLoginActivity.this, RegistrationsListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(UserLoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLogin> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (category.equalsIgnoreCase("history")){
                Intent intent = new Intent(UserLoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Intent intent = new Intent(UserLoginActivity.this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("category", category);
                intent.putExtra("shorttitle", shorttitle);
                intent.putExtra("conf_type", conf_type);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        if (category.equalsIgnoreCase("history")){
            Intent intent = new Intent(UserLoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            Intent intent = new Intent(UserLoginActivity.this, DashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("category", category);
            intent.putExtra("shorttitle", shorttitle);
            intent.putExtra("conf_type", conf_type);
            startActivity(intent);
        }
    }
}
