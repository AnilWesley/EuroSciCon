package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.applications.euroscicon.R;
import com.applications.euroscicon.activity.DashBoardActivity;
import com.applications.euroscicon.models.Events;
import com.applications.euroscicon.utils.MyAppPrefsManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private List<Events.ConferencesBean> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        movieResults = new ArrayList<>();
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        assert viewHolder != null;
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_home_list, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {

        Events.ConferencesBean result = movieResults.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat spf1 = new SimpleDateFormat("yyyy-MM-dd");


                Date newDate = null;
                Date newDate1 = null;

                try {
                    newDate = spf.parse(result.getStart_date());
                    newDate1 = spf1.parse(result.getEnd_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                spf = new SimpleDateFormat(MyAppPrefsManager.DD_MMM_YYYY_DATE_FORMAT, Locale.ENGLISH);
                spf1 = new SimpleDateFormat(MyAppPrefsManager.DD_MMM_YYYY_DATE_FORMAT1, Locale.ENGLISH);


                assert newDate != null;
                String date1 = spf.format(newDate);
                String[] data1 = date1.split(" ", 2);
                String month1 = "" + data1[0];
                String date_1 = "" + data1[1];

                assert newDate1 != null;
                String date2 = spf1.format(newDate1);
                String[] data2 = date2.split(" ", 2);
                String month2 = "" + data2[0];
                String date_2 = "" + data2[1];
                String[] data3 = date_2.split(", ", 2);
                String date_3=""+data3[0];

                String date3;
                if (date_1.equalsIgnoreCase(date_3)) {
                    date3=date2;
                } else if (month1.equalsIgnoreCase(month2)) {
                    date2 = date2.replace(month2, "");
                    date3 = date1 + " -" + date2;
                } else {
                    date3 = date1 + "-" + date2;
                }

                String text = Html.fromHtml(result.getTitle()).toString();


                movieVH.articleTitle.setText(text);

                String conf_type=result.getConf_type();
                movieVH.articleCity.setText(result.getCity() + ", " + result.getCountry());

                Log.d(TAG, "onBindViewHolder: "+result.getConf_type());

                if (conf_type.equalsIgnoreCase("webinar")){
                    movieVH.articleCity.setVisibility(View.GONE);
                    movieVH.confType.setVisibility(View.VISIBLE);
                    movieVH.confType.setText("Online Event");

                }else {
                    movieVH.confType.setVisibility(View.GONE);
                    movieVH.articleCity.setVisibility(View.VISIBLE);

                }

                movieVH.articleType.setText(result.getSubject());
                movieVH.articleDate.setText("" + date3);


                if (movieResults.get(position).getIcon_url() == null) {
                    movieVH.progressBar.setVisibility(View.GONE);
                } else {        // load movie thumbnail
                    Glide.with(context)
                            .load(result.getIcon_url())
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    movieVH.articleImage.setImageDrawable(resource);
                                    movieVH.progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                    movieVH.progressBar.setVisibility(View.GONE);
                                }

                            });

                }
                movieVH.parentLayout.setOnClickListener(view -> {

                    Intent intent = new Intent(context, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", movieResults.get(position).getId());
                    intent.putExtra("title", movieVH.articleTitle.getText().toString());
                    intent.putExtra("shorttitle", movieResults.get(position).getShort_name());
                    intent.putExtra("conf_type", movieResults.get(position).getConf_type());
                    context.startActivity(intent);

                });

                break;

            case LOADING:
//                Do nothing

                break;
        }

    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Events.ConferencesBean r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Events.ConferencesBean> moveResults) {
        for (Events.ConferencesBean result : moveResults) {
            add(result);
        }
    }

    private void remove(Events.ConferencesBean r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Events.ConferencesBean());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Events.ConferencesBean result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Events.ConferencesBean getItem(int position) {
        return movieResults.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    static class MovieVH extends RecyclerView.ViewHolder {
        @BindView(R.id.articleImage1)
        ImageView articleImage;
        @BindView(R.id.progressBar1)
        ProgressBar progressBar;
        @BindView(R.id.articleTitle1)
        TextView articleTitle;
        @BindView(R.id.articleDate1)
        TextView articleDate;
        @BindView(R.id.articleCity1)
        TextView articleCity;
        @BindView(R.id.articleType1)
        TextView articleType;
        @BindView(R.id.confType)
        TextView confType;
        @BindView(R.id.parentLayout)
        CardView parentLayout;

        MovieVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }


    protected static class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
