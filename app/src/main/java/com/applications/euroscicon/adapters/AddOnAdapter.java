package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.applications.euroscicon.R;
import com.applications.euroscicon.listener.CatClickListener1;
import com.applications.euroscicon.models.Categories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOnAdapter extends RecyclerView.Adapter<AddOnAdapter.MyViewHolder> {
    Context context;
    List<Categories> categories;
    private CatClickListener1 myListener;
    int count = 0;




    public AddOnAdapter(Context context, List<Categories> model, CatClickListener1 myListener) {
        this.context = context;
        this.categories = model;
        this.myListener = myListener;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = new ArrayList<>();
        this.categories = categories;
        notifyDataSetChanged();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtAmount)
        TextView txtAmount;
        @BindView(R.id.btnAddtoPlan)
        Button btnAddtoPlan;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_on_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categories dataBean = categories.get(position);
        holder.txtTitle.setText(categories.get(position).getTitle());
        holder.txtAmount.setText("+ "+ categories.get(position).getCurrencyType()+ categories.get(position).getPrice1());



        if (dataBean.getQuantity() == 0) {
            holder.btnAddtoPlan.setText("ADD TO PLAN");
        } else {
            holder.btnAddtoPlan.setText("ADDED");
        }


        holder.btnAddtoPlan.setOnClickListener(v -> {



          if (holder.btnAddtoPlan.getText().toString().equalsIgnoreCase("ADD TO PLAN")){
                holder.btnAddtoPlan.setText("ADDED");
                myListener.onAddClick(position, dataBean);

            }else {
                holder.btnAddtoPlan.setText("ADD TO PLAN");
               // myListener.catlistener(false,position,categories);
                myListener.onRemoveClick(position, dataBean);
            }

        });
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
