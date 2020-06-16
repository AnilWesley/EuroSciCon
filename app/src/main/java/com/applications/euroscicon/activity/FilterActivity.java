package com.applications.euroscicon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.CountriesExpandableListAdapter;
import com.applications.euroscicon.adapters.MyCheckBoxAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.CheckBoxItem;
import com.applications.euroscicon.models.CityItem;
import com.applications.euroscicon.models.CountryItem;
import com.applications.euroscicon.models.FilterData;
import com.applications.euroscicon.utils.ConstantManager;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {
    List<CheckBoxItem> cbSubjects = new ArrayList<>();
    List<CheckBoxItem> cbMonths = new ArrayList<>();

    MyCheckBoxAdapter myMyCheckBoxAdapter;
    String TAG = "RESPONSE_DATA";
    @BindView(R.id.rbSubjects)
    RadioButton rbSubjects;
    @BindView(R.id.rbCountries)
    RadioButton rbCountries;
    @BindView(R.id.rbMonth)
    RadioButton rbMonth;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.subjectList)
    ListView subjectList;
    @BindView(R.id.subjectLayout)
    LinearLayout subjectLayout;
    @BindView(R.id.countriesList)
    ExpandableListView countriesList;
    @BindView(R.id.countriesLayout)
    LinearLayout countriesLayout;

    @BindView(R.id.monthList)
    ListView monthList;
    @BindView(R.id.monthLayout)
    LinearLayout monthLayout;
    @BindView(R.id.btnApply)
    Button btnApply;


    ArrayList<CountryItem> countryItems;
    ArrayList<CityItem> arSubCategory;
    ArrayList<HashMap<String, String>> parentItems;
    ArrayList<ArrayList<HashMap<String, String>>> childItems;
    CountriesExpandableListAdapter countriesExpandableListAdapter;
    @BindView(R.id.btnClear)
    Button btnClear;
    int count = 0,subjcetcount,citycount,montcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filters");


        setupReferences();

    }


    private void setupReferences() {
        countryItems = new ArrayList<>();
        arSubCategory = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("page", 1);


        apiInterface.processDataCountryandCity(jsonObject).enqueue(new Callback<FilterData>() {
            @Override
            public void onResponse(@NotNull Call<FilterData> call, @NotNull Response<FilterData> response) {


                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().isStatus());

                    FilterData events = response.body();


                    if (events.isStatus()) {

                        // subjects list
                        List<FilterData.SubjectsBean> subjectsList = events.getSubjects();
                        Log.d(TAG, "onResponse: " + subjectsList.size());
                        cbSubjects.clear();
                        for (FilterData.SubjectsBean subjectsBean : subjectsList) {
                            cbSubjects.add(new CheckBoxItem(false, "", subjectsBean.getSubject()));
                        }
                        MyCheckBoxAdapter adapterSubjects = new MyCheckBoxAdapter(FilterActivity.this, cbSubjects);
                        subjectList.setAdapter(adapterSubjects);

                        // months list
                        List<FilterData.MonthsBean> monthsBeanList = events.getMonths();
                        Log.d(TAG, "onResponse: " + monthsBeanList.size());
                        cbMonths.clear();
                        for (FilterData.MonthsBean monthsBean : monthsBeanList) {
                            cbMonths.add(new CheckBoxItem(false, "", monthsBean.getMonth()));
                        }
                        MyCheckBoxAdapter adapterMonths = new MyCheckBoxAdapter(FilterActivity.this, cbMonths);
                        monthList.setAdapter(adapterMonths);

                        //countries/cities list
                        List<FilterData.CountryCitiesBean> resultBeanList = events.getCountry_cities();
                        for (FilterData.CountryCitiesBean countryItemList : resultBeanList) {
                            CountryItem countryItem = new CountryItem();
                            countryItem.setCountryId(countryItemList.getCountry_id());
                            countryItem.setCountryName(countryItemList.getCountry_name());


                            arSubCategory = new ArrayList<>();
                            List<FilterData.CountryCitiesBean.CitiesBean> citiesBeanList = countryItemList.getCities();
                            for (FilterData.CountryCitiesBean.CitiesBean citiesBean : citiesBeanList) {

                                CityItem cityItem = new CityItem();
                                cityItem.setCityId(citiesBean.getCity_id());
                                cityItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                                cityItem.setCityName(citiesBean.getCity_name());
                                arSubCategory.add(cityItem);
                            }

                            countryItem.setSubCity(arSubCategory);
                            countryItems.add(countryItem);


                        }


                        Log.d("TAG", "setupReferences: " + countryItems.size());


                        for (CountryItem data : countryItems) {
//                        Log.i("Item id",item.id);
                            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> mapParent = new HashMap<String, String>();

                            mapParent.put(ConstantManager.Parameter.COUNTRY_ID, data.getCountryId());
                            mapParent.put(ConstantManager.Parameter.COUNTRY_NAME, data.getCountryName());

                            int countIsChecked = 0;
                            for (CityItem cityItem : data.getSubCity()) {

                                HashMap<String, String> mapChild = new HashMap<String, String>();
                                mapChild.put(ConstantManager.Parameter.CITY_ID, cityItem.getCityId());
                                mapChild.put(ConstantManager.Parameter.CITY_NAME, cityItem.getCityName());
                                mapChild.put(ConstantManager.Parameter.COUNTRY_ID, cityItem.getCountryId());
                                mapChild.put(ConstantManager.Parameter.IS_CHECKED, cityItem.getIsChecked());

                                if (cityItem.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                                    countIsChecked++;
                                }
                                childArrayList.add(mapChild);
                            }

                            if (countIsChecked == data.getSubCity().size()) {

                                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
                            } else {
                                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
                            }

                            mapParent.put(ConstantManager.Parameter.IS_CHECKED, data.getIsChecked());
                            childItems.add(childArrayList);
                            parentItems.add(mapParent);


                        }


                        ConstantManager.parentItems = parentItems;
                        ConstantManager.childItems = childItems;

                        countriesExpandableListAdapter = new CountriesExpandableListAdapter(FilterActivity.this, parentItems, childItems, false);
                        countriesList.setAdapter(countriesExpandableListAdapter);

                    } else {
                        Toast.makeText(FilterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FilterData> call, @NotNull Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @OnClick({R.id.rbSubjects, R.id.rbCountries, R.id.rbMonth, R.id.btnApply,R.id.btnClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                setupReferences();
            case R.id.rbSubjects:
                subjectLayout.setVisibility(View.VISIBLE);
                countriesLayout.setVisibility(View.GONE);
                monthLayout.setVisibility(View.GONE);
                break;
            case R.id.rbCountries:
                subjectLayout.setVisibility(View.GONE);
                countriesLayout.setVisibility(View.VISIBLE);
                monthLayout.setVisibility(View.GONE);
                break;
            case R.id.rbMonth:
                subjectLayout.setVisibility(View.GONE);
                countriesLayout.setVisibility(View.GONE);
                monthLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btnApply:


                StringBuilder countryBuilder = new StringBuilder();
                StringBuilder cityBuilder = new StringBuilder();
                for (int i = 0; i < CountriesExpandableListAdapter.parentItems.size(); i++) {

                    String isChecked = CountriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.IS_CHECKED);

                    assert isChecked != null;
                    if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                        countryBuilder.append(CountriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.COUNTRY_NAME)).append("###");
                    }


                    for (int j = 0; j < CountriesExpandableListAdapter.childItems.get(i).size(); j++) {

                        String isChildChecked = CountriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                        assert isChildChecked != null;
                        if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                            cityBuilder.append(CountriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.CITY_NAME)).append("###");

                        }

                    }

                }


                String cityStr = cityBuilder.toString();

                String strCity = "";
                if (cityStr.length() > 0) {
                    strCity = cityStr.substring(0, cityStr.length() - 3);
                    //Log.e("CHECKBOXESONE", "" + strCity);
                    citycount=1;

                } else {
                    System.out.println(cityStr);
                    citycount=0;
                }


                // Subjects data

                StringBuilder subjectBuilder = new StringBuilder();
                for (int i = 0; i < cbSubjects.size(); i++) {
                    if (cbSubjects.get(i).isChecked()) {

                        subjectBuilder.append(cbSubjects.get(i).getItemString()).append("###");

                    }
                }
                String subjectStr = subjectBuilder.toString();

                String strSubject = "";
                if (subjectStr.length() > 0) {
                    strSubject = subjectStr.substring(0, subjectStr.length() - 3);
                    //Log.e("CHECKBOXESONE", "" + strSubject);
                    subjcetcount=1;

                } else {
                    System.out.println(subjectStr);
                    subjcetcount=0;
                }

                // Month data
                String monthStr;
                String strMonth = "";
                StringBuilder subCatBuilder = new StringBuilder();
                for (CheckBoxItem checkBox : cbMonths) {

                    if (checkBox.checked) {

                        subCatBuilder.append(checkBox.getItemString()).append("###");
                    }
                }
                monthStr = subCatBuilder.toString();
                if (monthStr.length() > 0) {
                    strMonth = monthStr.substring(0, monthStr.length() - 3);
                    // Log.e("strMonth", "" + strMonth);
                    montcount=1;
                }else {
                    montcount=0;
                }


                if (!strSubject.equalsIgnoreCase("") || !strCity.equalsIgnoreCase("") || !strMonth.equalsIgnoreCase("")){



                    count = subjcetcount+citycount+montcount;
                    Log.d(TAG, "onViewClicked: "+count);

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("subject", strSubject);
                    intent.putExtra("city", strCity);
                    intent.putExtra("month", strMonth);
                    intent.putExtra("filters", "true");
                    intent.putExtra("filterCount", ""+count);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Please select one Filter", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            /*Intent intent = new Intent(SubmitAbstractActivity.this, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", conf_id);
            intent.putExtra("title", actionTitle);
            startActivity(intent);*/
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
