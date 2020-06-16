package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.AddOnAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.listener.CatClickListener1;
import com.applications.euroscicon.models.AddOnItem;
import com.applications.euroscicon.models.Categories;
import com.applications.euroscicon.models.ConferenceProducts;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity1 extends AppCompatActivity implements CatClickListener1 {

    private static final String TAG = "RESPONSE_DATA";

    @BindView(R.id.radio0)
    RadioButton radio0;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.currency_group)
    RadioGroup currencyGroup;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    @BindView(R.id.spinnerCategoryProduct)
    Spinner spinnerCategoryProduct;
    @BindView(R.id.txtDate1)
    TextView txtDate1;
    @BindView(R.id.txtDate2)
    TextView txtDate2;
    @BindView(R.id.txtDate3)
    TextView txtDate3;
    @BindView(R.id.txthide)
    TextView txthide;
    @BindView(R.id.txtPrice1)
    TextView txtPrice1;
    @BindView(R.id.txtPrice2)
    TextView txtPrice2;
    @BindView(R.id.txtPrice3)
    TextView txtPrice3;
    @BindView(R.id.textAdd1)
    CheckBox textAdd1;
    @BindView(R.id.itemChildLayout1)
    LinearLayout itemChildLayout1;
    @BindView(R.id.catCard)
    CardView catCard;
    @BindView(R.id.spinnerCategoryYRF)
    Spinner spinnerCategoryYRF;
    @BindView(R.id.txtDate12)
    TextView txtDate12;
    @BindView(R.id.txtDate22)
    TextView txtDate22;
    @BindView(R.id.txtDate32)
    TextView txtDate32;
    @BindView(R.id.txthide2)
    TextView txthide2;
    @BindView(R.id.txtPrice12)
    TextView txtPrice12;
    @BindView(R.id.txtPrice22)
    TextView txtPrice22;
    @BindView(R.id.txtPrice32)
    TextView txtPrice32;
    @BindView(R.id.textAdd2)
    CheckBox textAdd2;
    @BindView(R.id.itemChildLayout2)
    LinearLayout itemChildLayout2;
    @BindView(R.id.yrfCard)
    CardView yrfCard;
    @BindView(R.id.spinnerCategoryEposter)
    Spinner spinnerCategoryEposter;
    @BindView(R.id.txtDate13)
    TextView txtDate13;
    @BindView(R.id.txtDate23)
    TextView txtDate23;
    @BindView(R.id.txtDate33)
    TextView txtDate33;
    @BindView(R.id.txthide3)
    TextView txthide3;
    @BindView(R.id.txtPrice13)
    TextView txtPrice13;
    @BindView(R.id.txtPrice23)
    TextView txtPrice23;
    @BindView(R.id.txtPrice33)
    TextView txtPrice33;
    @BindView(R.id.textAdd3)
    CheckBox textAdd3;
    @BindView(R.id.itemChildLayout3)
    LinearLayout itemChildLayout3;
    @BindView(R.id.ePosterCard)
    CardView ePosterCard;
    @BindView(R.id.addOnRecycler)
    RecyclerView addOnRecycler;
    @BindView(R.id.addOnCard)
    CardView addOnCard;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.btnProceed)
    Button btnProceed;
    @BindView(R.id.bottamLayout)
    LinearLayout bottamLayout;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;


    List<Categories> categoryTypeList = new ArrayList<>();
    List<Categories> categoryTypeListYRF = new ArrayList<>();
    List<Categories> categoryTypeListEposter = new ArrayList<>();
    List<ConferenceProducts.RegistrationProductsBean> registrationProductsBeans;
    private ArrayList<Categories> categoriesAddOnList = new ArrayList<>();
    ArrayList<AddOnItem> addOnItemArrayList = new ArrayList<>();

    ApiInterface apiInterface;
    CatClickListener1 catClickListener;
    AddOnAdapter addOnAdapter;
    String currentDate, categoryType, currencyType;
    String  conf_Id,userName,emailID,mobileNumber,country;
    //String  conf_Id="6036",title="dentist",userName="ANIL",emailID="anil@gmail.com",mobileNumber="123466",country="india";
    String catProduct_Id = "", catProduct = "";
    String yrfProduct_Id = "", yrfProduct = "";
    String ePosterProduct_Id = "", ePosterProduct = "";
    double totalCount = 0.00;
    int catPrice = 0, yrfPrice = 0, eposterPrice = 0, finalTotal = 0;
    String productType,actionTitle,shorttitle,conf_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment1);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose Products");

        if (getIntent() != null) {
            conf_Id = getIntent().getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = getIntent().getStringExtra("shorttitle");
            conf_type = getIntent().getStringExtra("conf_type");
            userName = getIntent().getStringExtra("userName");
            mobileNumber = getIntent().getStringExtra("mobileNumber");
            emailID = getIntent().getStringExtra("emailID");
            country = getIntent().getStringExtra("country");
        }
        currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());


        catClickListener = this;
        addOnRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        addOnRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        addOnAdapter = new AddOnAdapter(this, categoriesAddOnList, catClickListener);
        addOnRecycler.setAdapter(addOnAdapter);
        addOnAdapter.notifyDataSetChanged();

        currencyType = "\u00a3";
        getConferenceProducts1(currencyType);
        getConferenceProducts(currencyType, "Academic");
        totalCount = catPrice + yrfPrice + eposterPrice;
        calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);

        currencyGroup.setOnCheckedChangeListener((group, checkedId) -> {

            Log.d(TAG, "onCheckedChanged: " + checkedId);
            // checkedId is the RadioButton selected
            switch (checkedId) {
                case R.id.radio0:
                    currencyType = "\u00a3";

                    getConferenceProducts1(currencyType);
                    getConferenceProducts(currencyType, "Academic");
                    totalCount = catPrice + yrfPrice + eposterPrice;
                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);


                    break;

                case R.id.radio1:
                    // Do Something
                    currencyType = "\u20ac";

                    getConferenceProducts1(currencyType);
                    getConferenceProducts(currencyType, "Academic");
                    totalCount = catPrice + yrfPrice + eposterPrice;
                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
                    break;

                case R.id.radio2:
                    // Do Something
                    currencyType = "$";

                    getConferenceProducts1(currencyType);
                    getConferenceProducts(currencyType, "Academic");
                    totalCount = catPrice + yrfPrice + eposterPrice;
                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
                    break;
            }

        });


        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryType = spinnerCategory.getSelectedItem().toString();
                getConferenceProducts(currencyType, categoryType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void getConferenceProducts(String currencyType, String categoryType) {

        progressBar.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_Id);

        Log.d(TAG, "RESPONSE_ID" + jsonObject);

        apiInterface.processDataConferenceProducts(jsonObject).enqueue(new Callback<ConferenceProducts>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ConferenceProducts> call, @NotNull Response<ConferenceProducts> response) {


                if (response.isSuccessful()) {
                    // Stopping Shimmer Effect's animation after data is loaded to ListView

                    progressBar.setVisibility(View.GONE);

                    categoryTypeList.clear();
                    assert response.body() != null;
                    registrationProductsBeans = response.body().getRegistration_products();
                    for (ConferenceProducts.RegistrationProductsBean conferenceProducts : registrationProductsBeans) {

                        if (currencyType.equalsIgnoreCase("$")) {
                            if (categoryType.equalsIgnoreCase(conferenceProducts.getType())) {

                                categoryTypeList.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice1(),
                                        conferenceProducts.getPrice2(),
                                        conferenceProducts.getPrice3(),
                                        "$",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }

                        } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                            if (categoryType.equalsIgnoreCase(conferenceProducts.getType())) {

                                categoryTypeList.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice4(),
                                        conferenceProducts.getPrice5(),
                                        conferenceProducts.getPrice6(),
                                        "\u20ac",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }


                        } else if (currencyType.equalsIgnoreCase("\u00a3")) {
                            if (categoryType.equalsIgnoreCase(conferenceProducts.getType())) {

                                categoryTypeList.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice7(),
                                        conferenceProducts.getPrice8(),
                                        conferenceProducts.getPrice9(),
                                        "\u00a3",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }

                        }


                    }


                    if (categoryTypeList.size() == 0) {
                        catCard.setVisibility(View.GONE);
                    } else {
                        catCard.setVisibility(View.VISIBLE);
                    }


                    ArrayAdapter<Categories> adapterCat =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryTypeList);
                    adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategoryProduct.setAdapter(adapterCat);
                    adapterCat.notifyDataSetChanged();


                    spinnerCategoryProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Categories categories = (Categories) parent.getItemAtPosition(position);

                            txtPrice1.setText(categories.getCurrencyType() + categories.getPrice1());
                            txtPrice2.setText(categories.getCurrencyType() + categories.getPrice2());
                            txtPrice3.setText(categories.getCurrencyType() + categories.getPrice3());

                            textAdd1.setChecked(false);
                            addOnCard.setVisibility(View.GONE);
                            categoriesAddOnList.clear();
                            for (ConferenceProducts.RegistrationProductsBean conferenceProducts : registrationProductsBeans) {

                                if (currencyType.equalsIgnoreCase("$")) {
                                    if (conferenceProducts.getType().equalsIgnoreCase("addon")) {

                                        Log.d(TAG, "onItemSelected: " + conferenceProducts.getRegproducts_id());
                                        Categories categories1 = new Categories();
                                        categories1.setRegproducts_id(conferenceProducts.getRegproducts_id());
                                        categories1.setTitle(conferenceProducts.getProductname());
                                        categories1.setPrice1(conferenceProducts.getPrice1());
                                        categories1.setPrice2(conferenceProducts.getPrice2());
                                        categories1.setPrice3(conferenceProducts.getPrice3());
                                        categories1.setEarlyDate(conferenceProducts.getEarly());
                                        categories1.setNormalDate(conferenceProducts.getNormal());
                                        categories1.setFinalDate(conferenceProducts.getFinalX());
                                        categories1.setType(conferenceProducts.getType());
                                        categories1.setCurrencyType("$");
                                        categories1.setQuantity(0);
                                        categories1.setStatus("remove");
                                        categoriesAddOnList.add(categories1);

                                    }
                                } else if (currencyType.equalsIgnoreCase("\u00a3")) {
                                    if (conferenceProducts.getType().equalsIgnoreCase("addon")) {

                                        Categories categories1 = new Categories();
                                        categories1.setRegproducts_id(conferenceProducts.getRegproducts_id());
                                        categories1.setTitle(conferenceProducts.getProductname());
                                        categories1.setPrice1(conferenceProducts.getPrice1());
                                        categories1.setPrice2(conferenceProducts.getPrice2());
                                        categories1.setPrice3(conferenceProducts.getPrice3());
                                        categories1.setEarlyDate(conferenceProducts.getEarly());
                                        categories1.setNormalDate(conferenceProducts.getNormal());
                                        categories1.setFinalDate(conferenceProducts.getFinalX());
                                        categories1.setType(conferenceProducts.getType());
                                        categories1.setCurrencyType("\u00a3");
                                        categories1.setQuantity(0);
                                        categories1.setStatus("remove");
                                        categoriesAddOnList.add(categories1);

                                    }
                                } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                                    if (conferenceProducts.getType().equalsIgnoreCase("addon")) {

                                        Categories categories1 = new Categories();
                                        categories1.setRegproducts_id(conferenceProducts.getRegproducts_id());
                                        categories1.setTitle(conferenceProducts.getProductname());
                                        categories1.setPrice1(conferenceProducts.getPrice1());
                                        categories1.setPrice2(conferenceProducts.getPrice2());
                                        categories1.setPrice3(conferenceProducts.getPrice3());
                                        categories1.setEarlyDate(conferenceProducts.getEarly());
                                        categories1.setNormalDate(conferenceProducts.getNormal());
                                        categories1.setFinalDate(conferenceProducts.getFinalX());
                                        categories1.setType(conferenceProducts.getType());
                                        categories1.setCurrencyType("\u20ac");
                                        categories1.setQuantity(0);
                                        categories1.setStatus("remove");
                                        categoriesAddOnList.add(categories1);

                                    }
                                }
                            }

                            addOnAdapter.setCategories(categoriesAddOnList);


                            if (txtPrice1.isEnabled()) {

                                txtPrice1.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice1.setBackgroundResource(R.drawable.rounded_no_ouline);
                                catPrice = 0;

                            } else if (txtPrice2.isEnabled()) {

                                txtPrice2.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice2.setBackgroundResource(R.drawable.rounded_no_ouline);
                                catPrice = 0;

                            } else {

                                txtPrice3.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice3.setBackgroundResource(R.drawable.rounded_no_ouline);
                                catPrice = 0;
                            }

                            try {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

                                Date date1 = formatter.parse(currentDate);

                                Date date2 = formatter.parse(categories.getEarlyDate());
                                assert date2 != null;
                                if (date2.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice1.setTextColor(Color.GRAY);
                                    txtPrice1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice1.setEnabled(false);


                                } else {
                                    txtPrice1.setEnabled(true);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                                Date date3 = formatter.parse(categories.getNormalDate());
                                assert date3 != null;
                                if (date3.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice2.setTextColor(Color.GRAY);
                                    txtPrice2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice2.setEnabled(false);

                                } else {
                                    txtPrice2.setEnabled(true);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                               /* Date date4 = formatter.parse(categories.getFinalDate());
                                assert date4 != null;
                                if (date4.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice3.setTextColor(Color.GRAY);
                                    txtPrice3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice3.setEnabled(false);
                                    textAdd1.setVisibility(View.GONE);

                                } else {
                                    txtPrice3.setEnabled(true);
                                    textAdd1.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }
*/
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }


                            textAdd1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {


                                        if (categoriesAddOnList.size() == 0) {
                                            addOnCard.setVisibility(View.GONE);
                                        } else {
                                            addOnCard.setVisibility(View.VISIBLE);
                                        }
                                        //  getConferenceProducts(currencyType, categoryType);
                                        catProduct = categories.getTitle();
                                        catProduct_Id = categories.getRegproducts_id();

                                        if (txtPrice1.isEnabled()) {

                                            productType="earlybird";
                                            txtPrice1.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice1.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price11");
                                            catPrice = Integer.parseInt(categories.getPrice1());

                                        } else if (txtPrice2.isEnabled()) {
                                            productType="regClose";
                                            txtPrice2.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice2.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price22");
                                            catPrice = Integer.parseInt(categories.getPrice2());

                                        } else {
                                            productType="final";
                                            txtPrice3.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice3.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price33");
                                            catPrice = Integer.parseInt(categories.getPrice3());
                                        }


                                    } else {

                                        getConferenceProducts(currencyType, categoryType);
                                        addOnCard.setVisibility(View.GONE);
                                        addOnItemArrayList.clear();
                                        catProduct = "";
                                        catProduct_Id = "";

                                        if (txtPrice1.isEnabled()) {

                                            txtPrice1.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice1.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            catPrice = 0;

                                        } else if (txtPrice2.isEnabled()) {

                                            txtPrice2.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice2.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            catPrice = 0;

                                        } else {

                                            txtPrice3.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice3.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            catPrice = 0;
                                        }

                                    }


                                    totalCount = catPrice + yrfPrice + eposterPrice;

                                    //txtTotalPrice.setText(currencyType + String.format("%.2f", (double) totalCount));
                                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

            }

            @Override
            public void onFailure(@NotNull Call<ConferenceProducts> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void getConferenceProducts1(String currencyType) {

        progressBar.setVisibility(View.VISIBLE);

        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_Id);

        Log.d(TAG, "RESPONSE_ID" + jsonObject);

        apiInterface.processDataConferenceProducts(jsonObject).enqueue(new Callback<ConferenceProducts>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ConferenceProducts> call, @NotNull Response<ConferenceProducts> response) {


                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);

                    categoryTypeListYRF.clear();
                    categoryTypeListEposter.clear();
                    assert response.body() != null;
                    registrationProductsBeans = response.body().getRegistration_products();
                    for (ConferenceProducts.RegistrationProductsBean conferenceProducts : registrationProductsBeans) {


                        txtDate1.setText("On/Before\n" + conferenceProducts.getEarly());
                        txtDate2.setText("On/Before\n" + conferenceProducts.getNormal());
                        txtDate3.setText("Final Call\n" + conferenceProducts.getFinalX());

                        txtDate12.setText("On/Before\n" + conferenceProducts.getEarly());
                        txtDate22.setText("On/Before\n" + conferenceProducts.getNormal());
                        txtDate32.setText("Final Call\n" + conferenceProducts.getFinalX());

                        txtDate13.setText("On/Before\n" + conferenceProducts.getEarly());
                        txtDate23.setText("On/Before\n" + conferenceProducts.getNormal());
                        txtDate33.setText("Final Call\n" + conferenceProducts.getFinalX());

                        if (currencyType.equalsIgnoreCase("$")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("yrf")) {
                                categoryTypeListYRF.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice1(),
                                        conferenceProducts.getPrice2(),
                                        conferenceProducts.getPrice3(),
                                        "$",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("yrf")) {
                                categoryTypeListYRF.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice4(),
                                        conferenceProducts.getPrice5(),
                                        conferenceProducts.getPrice6(),
                                        "\u20ac",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        } else if (currencyType.equalsIgnoreCase("\u00a3")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("yrf")) {
                                categoryTypeListYRF.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice7(),
                                        conferenceProducts.getPrice8(),
                                        conferenceProducts.getPrice9(),
                                        "\u00a3",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        }

                        if (currencyType.equalsIgnoreCase("$")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("eitem")) {

                                categoryTypeListEposter.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice1(),
                                        conferenceProducts.getPrice2(),
                                        conferenceProducts.getPrice3(),
                                        "$",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        } else if (currencyType.equalsIgnoreCase("\u20ac")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("eitem")) {

                                categoryTypeListEposter.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice4(),
                                        conferenceProducts.getPrice5(),
                                        conferenceProducts.getPrice6(),
                                        "\u20ac",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        } else if (currencyType.equalsIgnoreCase("\u00a3")) {
                            if (conferenceProducts.getType().equalsIgnoreCase("eitem")) {

                                categoryTypeListEposter.add(new Categories(conferenceProducts.getRegproducts_id(),
                                        conferenceProducts.getProductname(),
                                        conferenceProducts.getPrice7(),
                                        conferenceProducts.getPrice8(),
                                        conferenceProducts.getPrice9(),
                                        "\u00a3",
                                        conferenceProducts.getType(),
                                        conferenceProducts.getEarly(),
                                        conferenceProducts.getNormal(),
                                        conferenceProducts.getFinalX(), "remove"));
                            }
                        }

                    }

                    if (categoryTypeListYRF.size() == 0) {
                        yrfCard.setVisibility(View.GONE);
                    } else {
                        yrfCard.setVisibility(View.VISIBLE);
                    }


                    ArrayAdapter<Categories> adapterYRF =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryTypeListYRF);
                    adapterYRF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategoryYRF.setAdapter(adapterYRF);
                    adapterYRF.notifyDataSetChanged();

                    spinnerCategoryYRF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Categories categories = (Categories) parent.getItemAtPosition(position);

                            txtPrice12.setText(categories.getCurrencyType() + categories.getPrice1());
                            txtPrice22.setText(categories.getCurrencyType() + categories.getPrice2());
                            txtPrice32.setText(categories.getCurrencyType() + categories.getPrice3());


                            textAdd2.setChecked(false);
                            if (txtPrice12.isEnabled()) {

                                txtPrice12.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice12.setBackgroundResource(R.drawable.rounded_no_ouline);
                                yrfPrice = 0;

                            } else if (txtPrice22.isEnabled()) {

                                txtPrice22.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice22.setBackgroundResource(R.drawable.rounded_no_ouline);
                                yrfPrice = 0;

                            } else {

                                txtPrice32.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice32.setBackgroundResource(R.drawable.rounded_no_ouline);
                                yrfPrice = 0;
                            }
                            try {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

                                Date date1 = formatter.parse(currentDate);

                                Date date2 = formatter.parse(categories.getEarlyDate());
                                assert date2 != null;
                                if (date2.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice12.setTextColor(Color.GRAY);
                                    txtPrice12.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice12.setEnabled(false);


                                } else {
                                    txtPrice12.setEnabled(true);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                                Date date3 = formatter.parse(categories.getNormalDate());
                                assert date3 != null;
                                if (date3.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice22.setTextColor(Color.GRAY);
                                    txtPrice22.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice22.setEnabled(false);

                                } else {
                                    txtPrice22.setEnabled(true);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                                /*Date date4 = formatter.parse(categories.getFinalDate());
                                assert date4 != null;
                                if (date4.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice32.setTextColor(Color.GRAY);
                                    txtPrice32.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice32.setEnabled(false);
                                    textAdd2.setVisibility(View.GONE);

                                } else {
                                    txtPrice32.setEnabled(true);
                                    textAdd2.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }*/

                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            textAdd2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        yrfProduct = categories.getTitle();
                                        yrfProduct_Id = categories.getRegproducts_id();
                                        if (txtPrice12.isEnabled()) {
                                            productType="earlybird";
                                            txtPrice12.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice12.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price11");
                                            yrfPrice = Integer.parseInt(categories.getPrice1());

                                        } else if (txtPrice22.isEnabled()) {
                                            productType="regClose";
                                            txtPrice22.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice22.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price22");
                                            yrfPrice = Integer.parseInt(categories.getPrice2());

                                        } else {
                                            productType="final";
                                            txtPrice32.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice32.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price33");
                                            yrfPrice = Integer.parseInt(categories.getPrice3());
                                        }
                                    } else {
                                        yrfProduct = "";
                                        yrfProduct_Id = "";
                                        if (txtPrice12.isEnabled()) {

                                            txtPrice12.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice12.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            yrfPrice = 0;
                                        } else if (txtPrice22.isEnabled()) {

                                            txtPrice22.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice22.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            yrfPrice = 0;
                                        } else {

                                            txtPrice32.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice32.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            yrfPrice = 0;
                                        }
                                    }

                                    totalCount = catPrice + yrfPrice + eposterPrice;
                                    // txtTotalPrice.setText(currencyType + String.format("%.2f", (double) totalCount));

                                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (categoryTypeListEposter.size() == 0) {
                        ePosterCard.setVisibility(View.GONE);
                    } else {
                        ePosterCard.setVisibility(View.VISIBLE);
                    }


                    ArrayAdapter<Categories> adaptereposter =
                            new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryTypeListEposter);
                    adaptereposter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategoryEposter.setAdapter(adaptereposter);
                    adaptereposter.notifyDataSetChanged();

                    spinnerCategoryEposter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Categories categories = (Categories) parent.getItemAtPosition(position);
                            txtPrice13.setText(categories.getCurrencyType() + categories.getPrice1());
                            txtPrice23.setText(categories.getCurrencyType() + categories.getPrice2());
                            txtPrice33.setText(categories.getCurrencyType() + categories.getPrice3());

                            textAdd3.setChecked(false);
                            if (txtPrice13.isEnabled()) {

                                txtPrice13.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice13.setBackgroundResource(R.drawable.rounded_no_ouline);
                                eposterPrice = 0;

                            } else if (txtPrice23.isEnabled()) {

                                txtPrice23.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice23.setBackgroundResource(R.drawable.rounded_no_ouline);
                                eposterPrice = 0;

                            } else {

                                txtPrice33.setTextColor(Color.parseColor("#ffe42828"));
                                txtPrice33.setBackgroundResource(R.drawable.rounded_no_ouline);
                                eposterPrice = 0;
                            }
                            try {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

                                Date date1 = formatter.parse(currentDate);

                                Date date2 = formatter.parse(categories.getEarlyDate());
                                assert date2 != null;
                                if (date2.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice13.setTextColor(Color.GRAY);
                                    txtPrice13.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice13.setEnabled(false);


                                } else {
                                    txtPrice13.setEnabled(true);

                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                                Date date3 = formatter.parse(categories.getNormalDate());
                                assert date3 != null;
                                if (date3.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice23.setTextColor(Color.GRAY);
                                    txtPrice23.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice23.setEnabled(false);

                                } else {
                                    txtPrice23.setEnabled(true);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }

                               /* Date date4 = formatter.parse(categories.getFinalDate());
                                assert date4 != null;
                                if (date4.compareTo(date1) < 0) {
                                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                                    txtPrice33.setTextColor(Color.GRAY);
                                    txtPrice33.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    txtPrice33.setEnabled(false);
                                    textAdd3.setVisibility(View.GONE);

                                } else {
                                    txtPrice33.setEnabled(true);
                                    textAdd3.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                                }*/

                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            textAdd3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        ePosterProduct = categories.getTitle();
                                        ePosterProduct_Id = categories.getRegproducts_id();
                                        if (txtPrice13.isEnabled()) {
                                            productType="earlybird";
                                            txtPrice13.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice13.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price11");
                                            eposterPrice = Integer.parseInt(categories.getPrice1());

                                        } else if (txtPrice23.isEnabled()) {
                                            productType="regClose";
                                            txtPrice23.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice23.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price22");
                                            eposterPrice = Integer.parseInt(categories.getPrice2());

                                        } else {
                                            productType="final";
                                            txtPrice33.setTextColor(Color.parseColor("#4CAF50"));
                                            txtPrice33.setBackgroundResource(R.drawable.rounded_ouline);
                                            Log.d(TAG, "bind: " + "price33");
                                            eposterPrice = Integer.parseInt(categories.getPrice3());
                                        }


                                    } else {
                                        ePosterProduct = "";
                                        ePosterProduct_Id = "";
                                        if (txtPrice13.isEnabled()) {

                                            txtPrice13.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice13.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            eposterPrice = 0;

                                        } else if (txtPrice23.isEnabled()) {

                                            txtPrice23.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice23.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            eposterPrice = 0;

                                        } else {

                                            txtPrice33.setTextColor(Color.parseColor("#ffe42828"));
                                            txtPrice33.setBackgroundResource(R.drawable.rounded_no_ouline);
                                            eposterPrice = 0;
                                        }
                                    }

                                    totalCount = catPrice + yrfPrice + eposterPrice;
                                    // txtTotalPrice.setText(currencyType + String.format("%.2f", (double) totalCount));

                                    calculateCartTotal("", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
                                }
                            });


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }

            }

            @Override
            public void onFailure(@NotNull Call<ConferenceProducts> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });


    }


    @Override
    public void onAddClick(int position, Categories categories) {

        int i = categoriesAddOnList.indexOf(categories);
        Categories updatedProduct = new Categories(categories.getRegproducts_id(),
                categories.getTitle(), categories.getPrice1(), categories.getPrice2(), categories.getPrice3(),
                categories.getCurrencyType(), categories.getType(), categories.getEarlyDate(), categories.getNormalDate(), categories.getFinalDate(), 1, "add");

        categoriesAddOnList.remove(categories);
        categoriesAddOnList.add(i, updatedProduct);

        addOnAdapter.notifyDataSetChanged();

        calculateCartTotal("add", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);
    }

    @Override
    public void onRemoveClick(int position, Categories categories) {
        int i = categoriesAddOnList.indexOf(categories);
        Categories updatedProduct = new Categories(categories.getRegproducts_id(),
                categories.getTitle(), categories.getPrice1(), categories.getPrice2(), categories.getPrice3(),
                categories.getCurrencyType(), categories.getType(), categories.getEarlyDate(), categories.getNormalDate(), categories.getFinalDate(), 0, "remove");

        categoriesAddOnList.remove(categories);
        categoriesAddOnList.add(i, updatedProduct);

        addOnAdapter.notifyDataSetChanged();

        calculateCartTotal("remove", catProduct, catProduct_Id, yrfProduct, yrfProduct_Id, ePosterProduct, ePosterProduct_Id);

    }


    // total Amount
    @SuppressLint("SetTextI18n")
    public void calculateCartTotal(String value, String catProduct, String catProduct_Id, String yrfProduct, String yrfProduct_Id, String ePosterProduct, String ePosterProduct_Id) {

        int grandTotal = 0;

        if (value.equalsIgnoreCase("remove") || value.equalsIgnoreCase("add")) {
            addOnItemArrayList.clear();
            for (Categories order : categoriesAddOnList) {

                grandTotal += Double.parseDouble(order.getPrice1()) * order.getQuantity();

                if (order.getStatus().equalsIgnoreCase("add")) {

                    addOnItemArrayList.add(new AddOnItem(order.getRegproducts_id(), order.getTitle(), order.getPrice1(), order.getStatus(), "Add-On Registrations"));
                    Log.d(TAG, "calculateCartTotal: " + order.getTitle());
                }

            }
        }


        finalTotal = (int) (grandTotal + totalCount);
        // totalCount = totalCount + grandTotal;
        Log.e("TOTAL", "" + "Items | " + "  DISCOUNT : " + grandTotal + "TOTAL_COUNT" + finalTotal);
        txtTotalPrice.setText(currencyType + String.format("%.2f", (double) finalTotal));


        // button Function
        if (finalTotal > 0) {
            btnProceed.setAlpha(0.9f);
            btnProceed.setEnabled(true);
        } else {
            btnProceed.setAlpha(0.5f);
            btnProceed.setEnabled(false);
        }
        btnProceed.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity1.this, CheckOutActivity.class);
            Bundle bundle=new Bundle();

            bundle.putString("conf_Id",conf_Id);
            bundle.putString("title",actionTitle);
            bundle.putString("shorttitle",shorttitle);
            bundle.putString("conf_type",conf_type);
            bundle.putString("userName",userName);
            bundle.putString("emailID",emailID);
            bundle.putString("mobileNumber",mobileNumber);
            bundle.putString("country",country);
            bundle.putString("productType",productType);
            bundle.putString("catProductTitle",categoryType);
            bundle.putString("catProduct",catProduct);
            bundle.putString("catProduct_Id",catProduct_Id);
            bundle.putString("catPrice", String.valueOf(catPrice));
            bundle.putString("yrfProduct",yrfProduct);
            bundle.putString("yrfProduct_Id",yrfProduct_Id);
            bundle.putString("yrfPrice", String.valueOf(yrfPrice));
            bundle.putString("ePosterProduct",ePosterProduct);
            bundle.putString("ePosterProduct_Id",ePosterProduct_Id);
            bundle.putString("eposterPrice", String.valueOf(eposterPrice));
            bundle.putString("currencyType",currencyType);
            bundle.putString("totalAmount", String.valueOf(finalTotal));
            //bundle.putSerializable("addonList", addOnItemArrayList);
            bundle.putParcelableArrayList("addonList", addOnItemArrayList);
            intent.putExtra("checkOutData",bundle);
            startActivity(intent);







        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
