package com.applications.euroscicon.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.AddOnAdapter;
import com.applications.euroscicon.adapters.CatpaymentAdapter;
import com.applications.euroscicon.adapters.EPosterAdapter;
import com.applications.euroscicon.adapters.YRFAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.listener.CatClickListener;
import com.applications.euroscicon.models.Categories;
import com.applications.euroscicon.models.ConferenceProducts;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements CatClickListener {

    private static final String TAG = "PaymentActivity";
    @BindView(R.id.paymentRecycler)
    RecyclerView paymentRecycler;
    @BindView(R.id.radio0)
    RadioButton radio0;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.currency_group)
    RadioGroup currencyGroup;
    @BindView(R.id.radioAcadamic)
    RadioButton radioAcadamic;
    @BindView(R.id.radioBusiness)
    RadioButton radioBusiness;
    @BindView(R.id.radioStudent)
    RadioButton radioStudent;
    @BindView(R.id.category_group)
    RadioGroup categoryGroup;
    @BindView(R.id.txtSubTitle)
    TextView txtSubTitle;
    @BindView(R.id.txtPrice1)
    TextView txtPrice1;
    @BindView(R.id.txtPrice2)
    TextView txtPrice2;
    @BindView(R.id.txtPrice3)
    TextView txtPrice3;
    @BindView(R.id.itemChildLayout)
    LinearLayout itemChildLayout;
    @BindView(R.id.txthide)
    TextView txthide;
    @BindView(R.id.yrfRecycler)
    RecyclerView yrfRecycler;
    @BindView(R.id.ePosterRecycler)
    RecyclerView ePosterRecycler;
    @BindView(R.id.addOnRecycler)
    RecyclerView addOnRecycler;
    @BindView(R.id.cardAddOn)
    CardView cardAddOn;
    @BindView(R.id.cardEposter)
    CardView cardEposter;
    @BindView(R.id.txtTotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.btnProceed)
    Button btnProceed;
    @BindView(R.id.bottamLayout)
    LinearLayout bottamLayout;

    private CatpaymentAdapter catpaymentAdapter;
    private YRFAdapter yrfAdapter;
    private EPosterAdapter ePosterAdapter;
    AddOnAdapter addOnAdapter;

    CatClickListener catClickListener;
    Double catAmount = 0.00;
    Double yrfAmount = 0.00;
    Double ePosterAmount = 0.00;

    String catType = "academic";
    String currencyType = "GBP";
    String conf_Id;
    private ArrayList<Categories> categoriesCatList = new ArrayList<>();
    private ArrayList<Categories> categoriesYRFList = new ArrayList<>();
    private ArrayList<Categories> categoriesePosterList = new ArrayList<>();
    private ArrayList<Categories> categoriesAddOnList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose Products");

        if (getIntent() != null) {
            conf_Id = getIntent().getStringExtra("id");
        }

        catClickListener = this;

        paymentRecycler.setLayoutManager(new LinearLayoutManager(this));
        paymentRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        catpaymentAdapter = new CatpaymentAdapter(this, categoriesCatList, catClickListener);
        paymentRecycler.setAdapter(catpaymentAdapter);

        yrfRecycler.setLayoutManager(new LinearLayoutManager(this));
        yrfRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        yrfAdapter = new YRFAdapter(this, categoriesYRFList, catClickListener);
        yrfRecycler.setAdapter(yrfAdapter);

        ePosterRecycler.setLayoutManager(new LinearLayoutManager(this));
        ePosterRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ePosterAdapter = new EPosterAdapter(this, categoriesePosterList, catClickListener);
        ePosterRecycler.setAdapter(ePosterAdapter);

       /* addOnRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        addOnRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        addOnAdapter = new AddOnAdapter(this, categoriesAddOnList, catClickListener);
        addOnRecycler.setAdapter(addOnAdapter);*/


        chooseCategory(currencyType);
        getConferenceProducts(catType, currencyType);
        getConferenceProducts1(false, currencyType);
        total(0.00,0.00,0.00);
        currencyGroup.setOnCheckedChangeListener((group, checkedId) -> {

            Log.d(TAG, "onCheckedChanged: " + checkedId);
            // checkedId is the RadioButton selected
            switch (checkedId) {
                case R.id.radio0:
                    // Do Something
                    currencyType = "GBP";
                    chooseCategory(currencyType);
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);

                    //txtTotalPrice.setText("\u00a3 "+String.format("%.2f", 0.00));
                    cardAddOn.setVisibility(View.GONE);

                    total(0.00,0.00,0.00);
                    break;

                case R.id.radio1:
                    // Do Something
                    currencyType = "Euro";
                    chooseCategory(currencyType);
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);

                  //  txtTotalPrice.setText("\u20ac "+String.format("%.2f", 0.00));
                    cardAddOn.setVisibility(View.GONE);

                    total(0.00,0.00,0.00);
                    break;

                case R.id.radio2:
                    // Do Something
                    currencyType = "USD";
                    chooseCategory(currencyType);
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);

                  //  txtTotalPrice.setText("$ "+String.format("%.2f", 0.00));;
                    cardAddOn.setVisibility(View.GONE);

                    total(0.00,0.00,0.00);

                    break;
            }

        });


    }

    private void chooseCategory(String currencyType) {
        categoryGroup.setOnCheckedChangeListener((group, checkedId) -> {

            Log.d(TAG, "onCheckedChanged: " + checkedId);
            // checkedId is the RadioButton selected
            switch (checkedId) {
                case R.id.radioAcadamic:
                    // Do Something
                    catType = "academic";
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);
                    total(0.00,0.00,0.00);
                    cardAddOn.setVisibility(View.GONE);
                    break;

                case R.id.radioBusiness:
                    // Do Something
                    catType = "business";
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);
                    total(0.00,0.00,0.00);
                    cardAddOn.setVisibility(View.GONE);
                    break;

                case R.id.radioStudent:
                    // Do Something
                    catType = "student";
                    getConferenceProducts(catType, currencyType);
                    getConferenceProducts1(false, currencyType);
                    total(0.00,0.00,0.00);
                    cardAddOn.setVisibility(View.GONE);

                    break;
            }

        });
    }

    private void getConferenceProducts(String catType, String currencyType) {

        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_Id);

        Log.d(TAG, "RESPONSE_ID" + jsonObject);

        apiInterface.processDataConferenceProducts(jsonObject).enqueue(new Callback<ConferenceProducts>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ConferenceProducts> call, @NotNull Response<ConferenceProducts> response) {


                if (response.isSuccessful()) {

                    assert response.body() != null;
                    List<ConferenceProducts.RegistrationProductsBean> registration_products = response.body().getRegistration_products();
                    categoriesCatList.clear();
                    for (ConferenceProducts.RegistrationProductsBean registrationProductsBean : registration_products) {


                        txtPrice1.setText("On/Before\n" + registrationProductsBean.getEarly());
                        txtPrice1.setTextColor(Color.BLACK);
                        txtPrice2.setText("On/Before\n" + registrationProductsBean.getNormal());
                        txtPrice2.setTextColor(Color.BLACK);
                        txtPrice3.setText("Final Call\n" + registrationProductsBean.getFinalX());
                        txtPrice3.setTextColor(Color.BLACK);


                        if (currencyType.equalsIgnoreCase("USD")) {


                            if (catType.equalsIgnoreCase(registrationProductsBean.getType())) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("$");
                                PaymentActivity.this.categoriesCatList.add(categories);

                            }

                        } else if (currencyType.equalsIgnoreCase("Euro")) {

                            if (catType.equalsIgnoreCase(registrationProductsBean.getType())) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u20ac");
                                PaymentActivity.this.categoriesCatList.add(categories);


                            }


                        } else if (currencyType.equalsIgnoreCase("GBP")) {

                            if (catType.equalsIgnoreCase(registrationProductsBean.getType())) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u00a3");
                                PaymentActivity.this.categoriesCatList.add(categories);

                               /* paymentItemList.add(new PaymentItem(false,registrationProductsBean.getProductname(),
                                        registrationProductsBean.getPrice7(), registrationProductsBean.getPrice8(), registrationProductsBean.getPrice9(), "\u00a3",registrationProductsBean.getType(),
                                        registrationProductsBean.getEarly(),registrationProductsBean.getNormal(),registrationProductsBean.getFinalX()));
*/
                            }

                        }

                        catpaymentAdapter.setCategories(categoriesCatList);

                        /*adapter = new SingleAdapter( PaymentActivity.this,employees,clickListener);
                        paymentRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();*/

                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<ConferenceProducts> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });


    }

    public void getConferenceProducts1(Boolean check, String currencyType) {

        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("conf_id", conf_Id);

        Log.d(TAG, "" + jsonObject);

        apiInterface.processDataConferenceProducts(jsonObject).enqueue(new Callback<ConferenceProducts>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ConferenceProducts> call, @NotNull Response<ConferenceProducts> response) {


                if (response.isSuccessful()) {

                    assert response.body() != null;
                    List<ConferenceProducts.RegistrationProductsBean> registration_products = response.body().getRegistration_products();
                    categoriesYRFList.clear();
                    categoriesePosterList.clear();
                    categoriesAddOnList.clear();
                    for (ConferenceProducts.RegistrationProductsBean registrationProductsBean : registration_products) {

                        if (currencyType.equalsIgnoreCase("USD")) {
                            if (registrationProductsBean.getType().equalsIgnoreCase("yrf")) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("$");
                                categoriesYRFList.add(categories);


                            } else if (registrationProductsBean.getType().equalsIgnoreCase("eitem")) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("$");
                                categoriesePosterList.add(categories);


                            } else if (registrationProductsBean.getType().equalsIgnoreCase("addon")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("$");
                                categoriesAddOnList.add(categories);
                            }


                        } else if (currencyType.equalsIgnoreCase("Euro")) {

                            if (registrationProductsBean.getType().equalsIgnoreCase("yrf")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u20ac");
                                PaymentActivity.this.categoriesYRFList.add(categories);

                            } else if (registrationProductsBean.getType().equalsIgnoreCase("eitem")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u20ac");
                                categoriesePosterList.add(categories);

                            } else if (registrationProductsBean.getType().equalsIgnoreCase("addon")) {

                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u20ac");
                                categoriesAddOnList.add(categories);

                            }


                        } else if (currencyType.equalsIgnoreCase("GBP")) {

                            if (registrationProductsBean.getType().equalsIgnoreCase("yrf")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u00a3");
                                categoriesYRFList.add(categories);

                            } else if (registrationProductsBean.getType().equalsIgnoreCase("eitem")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u00a3");
                                categoriesePosterList.add(categories);
                            } else if (registrationProductsBean.getType().equalsIgnoreCase("addon")) {
                                Categories categories = new Categories();
                                categories.setTitle(registrationProductsBean.getProductname());
                                categories.setPrice1(registrationProductsBean.getPrice1());
                                categories.setPrice2(registrationProductsBean.getPrice2());
                                categories.setPrice3(registrationProductsBean.getPrice3());
                                categories.setEarlyDate(registrationProductsBean.getEarly());
                                categories.setNormalDate(registrationProductsBean.getNormal());
                                categories.setFinalDate(registrationProductsBean.getFinalX());
                                categories.setType(registrationProductsBean.getType());
                                categories.setCurrencyType("\u00a3");
                                categoriesAddOnList.add(categories);
                            }

                        }

                    }

                    yrfAdapter.setCategories(categoriesYRFList,false);
                    ePosterAdapter.setCategories(categoriesePosterList,false);
                    addOnAdapter.setCategories(categoriesAddOnList);


                    // Cat card visible / hide
                    if (categoriesCatList.size() != 0) {
                        paymentRecycler.setVisibility(View.VISIBLE);
                    } else {
                        paymentRecycler.setVisibility(View.GONE);
                    }

                    // Eposter card visible / hide
                    if (categoriesePosterList.size() != 0) {
                        cardEposter.setVisibility(View.VISIBLE);
                    } else {
                        cardEposter.setVisibility(View.GONE);
                    }


                }

            }

            @Override
            public void onFailure(@NotNull Call<ConferenceProducts> call, @NotNull Throwable t) {
                //progressBar1.setVisibility(View.GONE);

            }
        });


    }




    @Override
    public void catlistener(boolean status, int position, String price) {
        if (catpaymentAdapter.getSelected() != null) {
            showToast(catpaymentAdapter.getSelected().getTitle());
            cardAddOn.setVisibility(View.VISIBLE);

            catAmount = Double.parseDouble(price);


        } else {
            showToast("No Selection cat");
            cardAddOn.setVisibility(View.GONE);

            catAmount = 0.00;
        }

        total(catAmount, yrfAmount, ePosterAmount);

    }

    @Override
    public void eposterlistener(int position, String price) {

        if (ePosterAdapter.getSelected() != null) {
            showToast(ePosterAdapter.getSelected().getTitle());

            ePosterAmount = Double.parseDouble(price);

        } else {
            showToast("No Selection Eposter");

            ePosterAmount = 0.00;
        }
        total(catAmount, yrfAmount, ePosterAmount);
    }

    @Override
    public void yrflistener(int position, String price) {

        if (yrfAdapter.getSelected() != null) {
            showToast(yrfAdapter.getSelected().getTitle());

            yrfAmount = Double.parseDouble(price);
        } else {
            showToast("No Selection yrf");

            yrfAmount = 0.00;

        }

        total(catAmount,yrfAmount,ePosterAmount);
    }

    private void total(Double catAmount, Double yrfAmount, Double ePosterAmount) {

        Double totalAmount = catAmount + yrfAmount + ePosterAmount;

        if (currencyType.equalsIgnoreCase("GBP")){
            txtTotalPrice.setText("\u00a3 "+String.format("%.2f", totalAmount));
        }else if (currencyType.equalsIgnoreCase("Euro")){
            txtTotalPrice.setText("\u20ac "+String.format("%.2f", totalAmount));
        }if (currencyType.equalsIgnoreCase("USD")){
            txtTotalPrice.setText("$ "+String.format("%.2f", totalAmount));;
        }

        Log.d(TAG, "listener: " + totalAmount + "-----------" + this.catAmount + "-----------" + this.yrfAmount + "-----------" + this.ePosterAmount);
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
