package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {
    private List<Events.ConferencesBean> model;
    private Context context;


    public HomeAdapter(List<Events.ConferencesBean> model, Context context) {
        this.model = model;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Events.ConferencesBean result = model.get(position); // Movie


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


        String text = Html.fromHtml(result.getTitle()).toString();

        holder.articleTitle.setText(text);
        if (model.get(position).getConf_type().equalsIgnoreCase("conference")) {
            holder.articleCity.setText(result.getCity() + ", " + result.getCountry());


        }else {

            holder.articleCity.setText(model.get(position).getConf_type());
        }
        holder.articleType.setText(result.getSubject());
        holder.articleDate.setText("" + date3);

        if (model.get(position).getIcon_url() == null) {
            holder.progressBar.setVisibility(View.GONE);
        } else {        // load movie thumbnail
            Glide.with(context)
                    .load(result.getIcon_url())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.articleImage.setImageDrawable(resource);
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                            holder.progressBar.setVisibility(View.GONE);
                        }

                    });

        }
        holder.parentLayout.setOnClickListener(view -> {

            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", model.get(position).getId());
            intent.putExtra("title", holder.articleTitle.getText().toString());
            intent.putExtra("shorttitle", model.get(position).getShort_name());
            intent.putExtra("conf_type", model.get(position).getConf_type());
            context.startActivity(intent);

        });

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //return position == 3 ? 0 : 1;
        return position;

    }

    @Override
    public int getItemCount() {

        return model.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.parentLayout)
        CardView parentLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<Events.ConferencesBean> filterdNames) {
        this.model = filterdNames;
        notifyDataSetChanged();
    }

}
