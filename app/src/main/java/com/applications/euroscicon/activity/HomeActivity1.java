package com.applications.euroscicon.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.HomeAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.Events;
import com.applications.euroscicon.utils.PaginationAdapterCallback;
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


public  class HomeActivity1 extends AppCompatActivity implements PaginationAdapterCallback {

    private static final String TAG = "RESPONSE_DATA";
    HomeAdapter adapter;
    GridLayoutManager linearLayoutManager;
    ApiInterface apiInterface;


    ArrayList<Events.ConferencesBean> names = new ArrayList<>();
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.eventsRecycle1)
    RecyclerView eventsRecycle1;
    @BindView(R.id.progressBar)
    LinearLayout progressBar1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).hide();
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() == 0) {
                    //Make the elements invisible
                    eventsRecycle1.setVisibility(View.GONE);

                } else if (charSequence.length() == 1) {
                    // Make fade in elements Visible first
                    eventsRecycle1.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input


                filter(editable.toString());
            }
        });


        //init seeventsRecycleice and load data
        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);

        loadEvents();


    }


    private void loadEvents() {

        //progressBar1.setVisibility(View.VISIBLE);
        callTopRatedMoviesApi().enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NotNull Call<Events> call, @NotNull Response<Events> response) {


                if (response.isSuccessful()) {
                    progressBar1.setVisibility(View.GONE);

                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().isStatus());

                    Events events = response.body();

                    names.addAll(events.getConferences());

                    if (events.isStatus()) {


                        List<Events.ConferencesBean> conferencesBeanList = events.getConferences();
                        Log.d(TAG, "onResponse: " + conferencesBeanList.size());
                        linearLayoutManager = new GridLayoutManager(HomeActivity1.this, 2);
                        eventsRecycle1.setLayoutManager(linearLayoutManager);
                        adapter = new HomeAdapter(conferencesBeanList, HomeActivity1.this);
                        eventsRecycle1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Events> call, @NotNull Throwable t) {
                t.printStackTrace();
                progressBar1.setVisibility(View.GONE);
            }
        });
    }


    private Call<Events> callTopRatedMoviesApi() {
        // prepare call in Retrofit 2.0
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("page", 1);


        Log.d(TAG, "" + jsonObject);

        return apiInterface.processDataAll(jsonObject);
    }

    /**
     * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
     *
     * @return
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void retryPageLoad() {
        // loadNextPage();
    }


  /*  @OnClick({R.id.error_btn_retry})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.error_btn_retry) {
            loadFirstPage();
        }
    }*/

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Events.ConferencesBean> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Events.ConferencesBean s : names) {
            //if the existing elements contains the search input
            if (s.getTitle().toLowerCase().contains(text.toLowerCase()) || s.getSubject().toLowerCase().startsWith(text.toLowerCase()) || s.getCountry().toLowerCase().startsWith(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }



        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }




}