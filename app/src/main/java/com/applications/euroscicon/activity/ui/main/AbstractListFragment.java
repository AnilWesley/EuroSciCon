package com.applications.euroscicon.activity.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.adapters.AbsrtactListAdapter;
import com.applications.euroscicon.api.ApiInterface;
import com.applications.euroscicon.api.RetrofitClient;
import com.applications.euroscicon.models.AbsrtactResponse;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.google.gson.JsonObject;


import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class AbstractListFragment extends Fragment {
    MyAppPrefsManager myAppPrefsManager;
    String userID;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "RESPONSE_DATA";
    @BindView(R.id.abstractRecycler)
    RecyclerView abstractRecycler;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    @BindView(R.id.constraintLayout)
    FrameLayout constraintLayout;
    @BindView(R.id.emptyView)
    TextView emptyView;


    public static AbstractListFragment newInstance(int index) {
        AbstractListFragment fragment = new AbstractListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_abstract_list, container, false);
        ButterKnife.bind(this, root);

        myAppPrefsManager = new MyAppPrefsManager(Objects.requireNonNull(getContext()));
        userID = myAppPrefsManager.getUserId();

        abstractList();
        return root;
    }

    public void abstractList() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient(getContext()).create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("app_user_id", userID);
        Log.d(TAG, "" + jsonObject);
        apiInterface.processDataAbstractList(jsonObject).enqueue(new Callback<AbsrtactResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AbsrtactResponse> call, @NotNull Response<AbsrtactResponse> response) {


                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.body());
                    progressBar.setVisibility(View.GONE);
                    AbsrtactResponse brochureDownload = response.body();
                    assert brochureDownload != null;
                    if (brochureDownload.isStatus()) {


                        List<AbsrtactResponse.ResultBean> resultBeans = brochureDownload.getResult();

                        abstractRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        AbsrtactListAdapter absrtactListAdapter = new AbsrtactListAdapter(getContext(), resultBeans);

                        abstractRecycler.setAdapter(absrtactListAdapter);
                        absrtactListAdapter.notifyDataSetChanged();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        abstractRecycler.setVisibility(View.GONE);
                        //Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                } 

            }

            @Override
            public void onFailure(@NotNull Call<AbsrtactResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

}