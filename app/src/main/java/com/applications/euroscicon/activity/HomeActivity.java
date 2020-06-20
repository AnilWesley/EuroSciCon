package com.applications.euroscicon.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.PaginationAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.firebase.ForceUpdateChecker;
import com.applications.euroscicon.models.Events;
import com.applications.euroscicon.utils.ConstantValues;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.applications.euroscicon.utils.PaginationScrollListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ForceUpdateChecker.OnUpdateNeededListener {

    private static final String TAG = "RESPONSE_DATA";

    PaginationAdapter adapter;
    GridLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

   /* @BindView(R.id.main_progress)
    ProgressBar progressBar;*/

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    ApiInterface apiInterface;
    @BindView(R.id.eventsRecycle1)
    RecyclerView rv;

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etSearch)
    TextView etSearch;


    @BindView(R.id.cardSearch)
    CardView cardSearch;
    boolean doubleBackToExitPressedOnce = false;

    String subject, city, month, filterValue, filterCount;
    MyAppPrefsManager myAppPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Objects.requireNonNull(toolbar).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        toolbar.setTitle("");
        myAppPrefsManager = new MyAppPrefsManager(HomeActivity.this);
        if (getIntent().getExtras() != null) {
            subject = getIntent().getStringExtra("subject");
            city = getIntent().getStringExtra("city");
            month = getIntent().getStringExtra("month");
            filterValue = getIntent().getStringExtra("filters");
            filterCount = getIntent().getStringExtra("filterCount");
            etSearch.setText("Filters (" + filterCount + ")");

        }


        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, FilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //init seeventsRecycleice and load data
        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);

        // loadEvents();


        adapter = new PaginationAdapter(this);

        linearLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //init service and load data
        apiInterface = RetrofitClient.getClient(this).create(ApiInterface.class);

        loadFirstPage();

    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        progressBar.setVisibility(View.VISIBLE);
        callTopRatedMoviesApi().enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NotNull Call<Events> call, @NotNull Response<Events> response) {
                // Got data. Send it to adapter

                List<Events.ConferencesBean> results = fetchResults(response);
                Log.d(TAG, "onResponse: " + results.size());

                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(@NotNull Call<Events> call, @NotNull Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });

    }

    /**
     * @param response extracts List<{@link Events.ConferencesBean>} from response
     * @return
     */
    private List<Events.ConferencesBean> fetchResults(Response<Events> response) {
        Events topRatedMovies = response.body();
        assert topRatedMovies != null;
        TOTAL_PAGES = Integer.parseInt(topRatedMovies.getTotal_pages());
        Log.d(TAG, "fetchResults: " + TOTAL_PAGES);
        return topRatedMovies.getConferences();
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<Events>() {
            @Override
            public void onResponse(@NotNull Call<Events> call, @NotNull Response<Events> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<Events.ConferencesBean> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
                Log.d(TAG, "onResponse: " + isLoading);
            }

            @Override
            public void onFailure(@NotNull Call<Events> call, @NotNull Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }

    /**
     * Performs a Retrofit call to the top rated movies API.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<Events> callTopRatedMoviesApi() {

        JsonObject jsonObject = new JsonObject();

        if (filterValue != null) {
            jsonObject.addProperty("filters", "true");
            jsonObject.addProperty("subjects", subject);
            jsonObject.addProperty("cities", city);
            jsonObject.addProperty("months", month);
            jsonObject.addProperty("page", currentPage);
        } else {
            jsonObject.addProperty("page", currentPage);
            jsonObject.addProperty("filters", "false");
        }


        Log.d(TAG, "" + jsonObject);
        return apiInterface.processDataAll(
                jsonObject
        );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            ConstantValues.shareMyApp(HomeActivity.this);
        } else if (id == R.id.nav_registrations) {
            if (ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn()) {
                Intent intent = new Intent(HomeActivity.this, RegistrationsListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } else {
                Intent intent = new Intent(HomeActivity.this, UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("category", "history");
                startActivity(intent);
            }
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, ContactUsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue.")
                .setPositiveButton("Update",
                        (dialog12, which) ->
                                redirectStore(updateUrl)).setNegativeButton("No, thanks",
                        (dialog1, which) ->
                                dialog1.dismiss()).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
