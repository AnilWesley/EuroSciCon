package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.applications.euroscicon.R;
import com.applications.euroscicon.models.RegistrationsListResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationsListAdapter extends RecyclerView.Adapter<RegistrationsListAdapter.MyViewHolder> {
    Context context;
    List<RegistrationsListResponse.ResultBean> categories;



    public RegistrationsListAdapter(Context context, List<RegistrationsListResponse.ResultBean> model) {
        this.context = context;
        this.categories = model;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtOrderNo)
        TextView txtOrderNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.registration_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtTitle.setText(Html.fromHtml(categories.get(position).getTitle()));
        holder.txtOrderNo.setText("Order Id : "+categories.get(position).getOrder_no());

        holder.txtPrice.setText(categories.get(position).getCurrency()+" " +categories.get(position).getPrice());


    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //return position == 6 ? 0 : 1;
        return position;
    }

    @Override
    public int getItemCount() {
        return categories.size();

    }


}
