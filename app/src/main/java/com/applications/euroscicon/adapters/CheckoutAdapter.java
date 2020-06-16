package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.applications.euroscicon.R;
import com.applications.euroscicon.listener.CatClickListener1;
import com.applications.euroscicon.models.AddOnItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {
    Context context;
    List<AddOnItem> categories;
    private String currencyType;
    private CatClickListener1 myListener;


    public CheckoutAdapter(Context context, List<AddOnItem> model, CatClickListener1 myListener, String currencyType) {
        this.context = context;
        this.categories = model;
        this.myListener = myListener;
        this.currencyType = currencyType;
    }

    public void setCategories(ArrayList<AddOnItem> categories) {
        this.categories = new ArrayList<>();
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtSubTitle)
        TextView txtSubTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtTitle.setText(categories.get(position).getTitle());
        holder.txtSubTitle.setText(categories.get(position).getSubTitle());
        holder.txtPrice.setText(" "+currencyType+ categories.get(position).getPrice());


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
