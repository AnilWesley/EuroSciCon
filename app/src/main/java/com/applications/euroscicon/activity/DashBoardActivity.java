package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.BannerAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.ConferenceBanner;
import com.applications.euroscicon.utils.ConstantValues;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.gson.JsonObject;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.linearBrochureDownload)
    LinearLayout linearBrochureDownload;
    @BindView(R.id.linearSessionsTracks)
    LinearLayout linearSessionsTracks;
    @BindView(R.id.linearChat)
    LinearLayout linearChat;
    @BindView(R.id.confType)
    TextView confType;



    private static final String TAG = "RESPONSE_DATA";
    @BindView(R.id.main_container)
    LinearLayout mainContainer;


    @BindView(R.id.slider_image)
    SliderView sliderImage;

    String id, title1, shorttitle, conf_type;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.subject)
    TextView subject;
    @BindView(R.id.progressBarFull)
    LinearLayout progressBar1;
    @BindView(R.id.linearRegister)
    LinearLayout linearRegister;
    @BindView(R.id.linearSubmitAbstract)
    LinearLayout linearSubmitAbstract;
    @BindView(R.id.linearFeedback)
    LinearLayout linearFeedback;

    MyAppPrefsManager myAppPrefsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_acitivity);
        ButterKnife.bind(this);

        myAppPrefsManager = new MyAppPrefsManager(DashBoardActivity.this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);





        if (getIntent() != null) {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            title1 = intent.getStringExtra("title");
            shorttitle = intent.getStringExtra("shorttitle");
            conf_type = intent.getStringExtra("conf_type");
            getSupportActionBar().setTitle(shorttitle);
        }


        getSlidesData();


    }


    @OnClick({R.id.linearRegister, R.id.linearSubmitAbstract, R.id.linearBrochureDownload, R.id.linearFeedback, R.id.linearSessionsTracks})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linearRegister:

                if (ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn()) {
                    Intent intent = new Intent(DashBoardActivity.this, PersonalInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(DashBoardActivity.this, UserLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    intent.putExtra("category", "register");
                    startActivity(intent);
                }


                break;
            case R.id.linearSubmitAbstract:


                if (ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn()) {
                    Intent intent = new Intent(DashBoardActivity.this, SubmitAbstractActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(DashBoardActivity.this, UserLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    intent.putExtra("category", "abstract");
                    startActivity(intent);
                }

                break;

            case R.id.linearBrochureDownload:
                if (ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn()) {
                    Intent intent = new Intent(DashBoardActivity.this, BrochureDownloadActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(DashBoardActivity.this, UserLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    intent.putExtra("category", "brochure");
                    startActivity(intent);
                }
                break;

            case R.id.linearFeedback:
                if (ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn()) {
                    Intent intent = new Intent(DashBoardActivity.this, FeedBackActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(DashBoardActivity.this, UserLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", id);
                    intent.putExtra("title", title1);
                    intent.putExtra("shorttitle", shorttitle);
                    intent.putExtra("conf_type", conf_type);
                    intent.putExtra("category", "feedback");
                    startActivity(intent);
                }
                break;

            case R.id.linearSessionsTracks:
                Intent intent = new Intent(DashBoardActivity.this, SessionsandTracksActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", id);
                intent.putExtra("title", title1);
                intent.putExtra("shorttitle", shorttitle);
                intent.putExtra("conf_type", conf_type);
                startActivity(intent);
                break;

        }
    }


    public void getSlidesData() {

        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", id);

        Log.d(TAG, "" + jsonObject);

        apiInterface.processDataConference(jsonObject).enqueue(new Callback<ConferenceBanner>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ConferenceBanner> call, @NotNull Response<ConferenceBanner> response) {


                if (response.isSuccessful()) {
                    progressBar1.setVisibility(View.GONE);
                    ConferenceBanner list = response.body();

                    assert list != null;
                    if (list.isStatus()) {
                        List<ConferenceBanner.ConferenceBean> modelList = list.getConference();
                        BannerAdapter adapter = new BannerAdapter(DashBoardActivity.this, modelList);
                        sliderImage.setSliderAdapter(adapter);
                        sliderImage.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderImage.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                        sliderImage.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                        sliderImage.setIndicatorRadius(5);
                        sliderImage.setIndicatorSelectedColor(Color.WHITE);
                        sliderImage.setIndicatorUnselectedColor(Color.GRAY);
                        sliderImage.startAutoCycle();
                        sliderImage.setOnIndicatorClickListener(position -> sliderImage.setCurrentPagePosition(position));


                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat spf1 = new SimpleDateFormat("yyyy-MM-dd");


                        Date newDate = null;
                        Date newDate1 = null;

                        try {
                            newDate = spf.parse(modelList.get(0).getStart_date());
                            newDate1 = spf1.parse(modelList.get(0).getEnd_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        spf = new SimpleDateFormat(MyAppPrefsManager.DD_MMM_YYYY_DATE_FORMAT, Locale.ENGLISH);
                        spf1 = new SimpleDateFormat(MyAppPrefsManager.DD_MMM_YYYY_DATE_FORMAT1, Locale.ENGLISH);


                        assert newDate != null;
                        String date1 = spf.format(newDate);
                        String[] data1 = date1.split(" ", 2);
                        String month1 = "" + data1[0];

                        assert newDate1 != null;
                        String date2 = spf1.format(newDate1);
                        String[] data2 = date2.split(" ", 2);
                        String month2 = "" + data2[0];

                        String date3;
                        if (month1.equalsIgnoreCase(month2)) {
                            date2 = date2.replace(month2, "");
                            date3 = date1 + " -" + date2;
                        } else {
                            date3 = date1 + "-" + date2;
                        }
                        date.setText(date3);


                        String text = Html.fromHtml(modelList.get(0).getTitle()).toString();
                        title.setText(text);

                        if (conf_type.equalsIgnoreCase("conference")) {

                            location.setText(modelList.get(0).getCity() + " ," + modelList.get(0).getCountry());
                            confType.setVisibility(View.GONE);

                        } else {

                            location.setVisibility(View.GONE);
                            confType.setText("Online Event");
                            //confType.setText(modelList.get(0).getConf_type());
                        }


                        subject.setText(modelList.get(0).getSubject());

                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<ConferenceBanner> call, @NotNull Throwable t) {
                progressBar1.setVisibility(View.GONE);

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(DashBoardActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = new Intent(DashBoardActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }


}
