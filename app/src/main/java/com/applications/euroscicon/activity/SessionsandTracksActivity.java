package com.applications.euroscicon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.SessionsExpandableListAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.Sessions;
import com.applications.euroscicon.models.SubTrackNames;
import com.applications.euroscicon.models.TrackNames;
import com.applications.euroscicon.utils.ConstantManager;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionsandTracksActivity extends AppCompatActivity {

    String conf_id, TAG = "RESPONSE_DATA";
    String actionTitle, shorttitle, conf_type, description;


    ArrayList<TrackNames> countryItems;
    ArrayList<SubTrackNames> arSubCategory;
    ArrayList<HashMap<String, String>> parentItems;
    ArrayList<ArrayList<HashMap<String, String>>> childItems;
    SessionsExpandableListAdapter countriesExpandableListAdapter;
    @BindView(R.id.sessionandTrackList)
    ExpandableListView sessionandTrackList;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessionsand_tracks);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sessions and Tracks");
        if (getIntent() != null) {
            Intent intent = getIntent();
            conf_id = intent.getStringExtra("id");
            actionTitle = getIntent().getStringExtra("title");
            shorttitle = getIntent().getStringExtra("shorttitle");
            conf_type = getIntent().getStringExtra("conf_type");

        }

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

        jsonObject.addProperty("conf_id", conf_id);


        apiInterface.processDataSessionsandTracks(jsonObject).enqueue(new Callback<Sessions>() {
            @Override
            public void onResponse(@NotNull Call<Sessions> call, @NotNull Response<Sessions> response) {


                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().isStatus());

                    Sessions events = response.body();


                    if (events.isStatus()) {


                        //countries/cities list
                        List<Sessions.TracksBean> resultBeanList = events.getTracks();
                        for (Sessions.TracksBean countryItemList : resultBeanList) {
                            TrackNames countryItem = new TrackNames();
                            countryItem.setCountryId(countryItemList.getId());
                            countryItem.setCountryName(countryItemList.getTrackName());
                            countryItem.setDescription(countryItemList.getDescription());


                            arSubCategory = new ArrayList<>();
                            List<Sessions.TracksBean.SubTracksBean> citiesBeanList = countryItemList.getSub_tracks();
                            for (Sessions.TracksBean.SubTracksBean citiesBean : citiesBeanList) {

                                SubTrackNames cityItem = new SubTrackNames();
                                cityItem.setCityName(citiesBean.getTrackName());
                                arSubCategory.add(cityItem);
                            }

                            countryItem.setSubCity(arSubCategory);
                            countryItems.add(countryItem);


                        }


                        Log.d("TAG", "setupReferences: " + countryItems.size());


                        for (TrackNames data : countryItems) {
//                        Log.i("Item id",item.id);
                            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> mapParent = new HashMap<String, String>();

                            description = data.getDescription();

                            mapParent.put(ConstantManager.Parameter.COUNTRY_ID, data.getCountryId());
                            mapParent.put(ConstantManager.Parameter.COUNTRY_NAME, data.getCountryName());
                            mapParent.put(ConstantManager.Parameter.COUNTRY_DESCRIPTION, description);


                            for (SubTrackNames cityItem : data.getSubCity()) {

                                HashMap<String, String> mapChild = new HashMap<String, String>();

                                mapChild.put(ConstantManager.Parameter.CITY_NAME, cityItem.getCityName());
                                mapChild.put(ConstantManager.Parameter.COUNTRY_ID, cityItem.getCountryId());
                                mapChild.put(ConstantManager.Parameter.COUNTRY_DESCRIPTION, description);


                                childArrayList.add(mapChild);
                            }

                            childItems.add(childArrayList);
                            parentItems.add(mapParent);


                        }


                        ConstantManager.parentItems = parentItems;
                        ConstantManager.childItems = childItems;


                        countriesExpandableListAdapter = new SessionsExpandableListAdapter(SessionsandTracksActivity.this, parentItems, childItems);
                        sessionandTrackList.setAdapter(countriesExpandableListAdapter);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SessionsandTracksActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Sessions> call, @NotNull Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
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

}