package com.applications.euroscicon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.applications.euroscicon.R;
import com.applications.euroscicon.listener.CatClickListener;
import com.applications.euroscicon.models.Categories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EPosterAdapter extends RecyclerView.Adapter<EPosterAdapter.SingleViewHolder> {

    private CatClickListener myListener;
    private Context context;
    private ArrayList<Categories> categories;
    private boolean check;
    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;

    public EPosterAdapter(Context context, ArrayList<Categories> categories, CatClickListener myListener) {
        this.context = context;
        this.categories = categories;
        this.myListener = myListener;
    }

    public void setCategories(ArrayList<Categories> categories,  boolean check) {
        this.categories = new ArrayList<>();
        this.categories = categories;
        this.check = check;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSubTitle)
        TextView txtSubTitle;
        @BindView(R.id.txtPrice1)
        TextView txtPrice1;
        @BindView(R.id.txtPrice2)
        TextView txtPrice2;
        @BindView(R.id.txtPrice3)
        TextView txtPrice3;
        @BindView(R.id.itemChildLayout)
        LinearLayout itemChildLayout;
        @BindView(R.id.imageView)
        ImageView imageView;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Categories categories) {
           /* if (checkedPosition == -1) {
                imageView.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }*/

            txtSubTitle.setText(categories.getTitle());
            txtPrice1.setText(categories.getCurrencyType() + categories.getPrice1());
            txtPrice2.setText(categories.getCurrencyType() + categories.getPrice2());
            txtPrice3.setText(categories.getCurrencyType() + categories.getPrice3());

            String currentDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());


            try {

                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

                Date date1 = formatter.parse(currentDate);
                Date date2 = formatter.parse(categories.getEarlyDate());
                Date date3 = formatter.parse(categories.getNormalDate());


                assert date2 != null;
                if (date2.compareTo(date1) < 0) {
                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                    txtPrice1.setTextColor(Color.GRAY);
                    txtPrice1.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    txtPrice1.setEnabled(false);

                } else {
                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                }


                assert date3 != null;
                if (date3.compareTo(date1) < 0) {
                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                    txtPrice2.setTextColor(Color.GRAY);
                    txtPrice2.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    txtPrice2.setEnabled(false);
                } else {
                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                }

                Date date4 = formatter.parse(categories.getFinalDate());
                assert date4 != null;
                if (date4.compareTo(date1) < 0) {
                    Log.d(TAG, "onBindViewHolder:  current date is greater than early date ");
                    txtPrice3.setTextColor(Color.GRAY);
                    txtPrice3.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    txtPrice3.setEnabled(false);
                } else {
                    Log.d(TAG, "onBindViewHolder:  current date is less than early date ");
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            }




            if (!check){
                if (txtPrice1.isEnabled()) {

                    txtPrice1.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice1.setBackgroundResource(R.drawable.rounded_no_ouline);

                } else if (txtPrice2.isEnabled()) {

                    txtPrice2.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice2.setBackgroundResource(R.drawable.rounded_no_ouline);

                } else {

                    txtPrice3.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice3.setBackgroundResource(R.drawable.rounded_no_ouline);
                }
            }


            // imageView.setVisibility(employee.isSelected() ? View.VISIBLE : View.GONE);

            if (categories.isChecked()) {
                imageView.setImageResource(R.drawable.ic_check_circle);
                if (txtPrice1.isEnabled()) {

                    txtPrice1.setTextColor(Color.parseColor("#4CAF50"));
                    txtPrice1.setBackgroundResource(R.drawable.rounded_ouline);
                    myListener.eposterlistener(checkedPosition,  categories.getPrice1());
                    Log.d(TAG, "bind: " + "price11");

                } else if (txtPrice2.isEnabled()) {

                    txtPrice2.setTextColor(Color.parseColor("#4CAF50"));
                    txtPrice2.setBackgroundResource(R.drawable.rounded_ouline);
                    myListener.eposterlistener(checkedPosition,  categories.getPrice2());
                    Log.d(TAG, "bind: " + "price22");


                } else {

                    txtPrice3.setTextColor(Color.parseColor("#4CAF50"));
                    txtPrice3.setBackgroundResource(R.drawable.rounded_ouline);
                    myListener.eposterlistener(checkedPosition,  categories.getPrice3());
                    Log.d(TAG, "bind: " + "price33");
                }

            } else {
                imageView.setImageResource(R.drawable.ic_uncheck_circle);
                myListener.eposterlistener(checkedPosition,"0.00");
                if (txtPrice1.isEnabled()) {


                    txtPrice1.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice1.setBackgroundResource(R.drawable.rounded_no_ouline);

                } else if (txtPrice2.isEnabled()) {

                    txtPrice2.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice2.setBackgroundResource(R.drawable.rounded_no_ouline);

                } else {

                    txtPrice3.setTextColor(Color.parseColor("#ffe42828"));
                    txtPrice3.setBackgroundResource(R.drawable.rounded_no_ouline);
                }
            }

            itemView.setOnClickListener(view -> {
                // imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.ic_check_circle);
                if (checkedPosition != getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = getAdapterPosition();
                   // myListener.listener(checkedPosition,2);

                    if (txtPrice1.isEnabled()) {

                        txtPrice1.setTextColor(Color.parseColor("#4CAF50"));
                        txtPrice1.setBackgroundResource(R.drawable.rounded_ouline);
                        //Toast.makeText(mContext, "" + mList.get(this.selectedPosition).getPrice1(), Toast.LENGTH_SHORT).show();
                        myListener.eposterlistener(checkedPosition,  categories.getPrice1());
                    } else if (txtPrice2.isEnabled()) {

                        txtPrice2.setTextColor(Color.parseColor("#4CAF50"));
                        txtPrice2.setBackgroundResource(R.drawable.rounded_ouline);
                        //Toast.makeText(mContext, "" + check, Toast.LENGTH_SHORT).show();
                        myListener.eposterlistener(checkedPosition, categories.getPrice2());
                    } else {

                        txtPrice3.setTextColor(Color.parseColor("#4CAF50"));
                        txtPrice3.setBackgroundResource(R.drawable.rounded_ouline);
                        //Toast.makeText(mContext, "" + check, Toast.LENGTH_SHORT).show();
                        myListener.eposterlistener(checkedPosition,  categories.getPrice3());
                    }

                } else {
                    // imageView.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_uncheck_circle);
                    checkedPosition = -1;
                    myListener.eposterlistener(checkedPosition,"");

                    if (txtPrice1.isEnabled()) {

                        txtPrice1.setTextColor(Color.parseColor("#ffe42828"));
                        txtPrice1.setBackgroundResource(R.drawable.rounded_no_ouline);

                    } else if (txtPrice2.isEnabled()) {

                       txtPrice2.setTextColor(Color.parseColor("#ffe42828"));
                       txtPrice2.setBackgroundResource(R.drawable.rounded_no_ouline);

                    } else {

                        txtPrice3.setTextColor(Color.parseColor("#ffe42828"));
                        txtPrice3.setBackgroundResource(R.drawable.rounded_no_ouline);
                    }
                }


            });

        }

    }

    public Categories getSelected() {
        if (checkedPosition != -1) {
            return categories.get(checkedPosition);
        }
        return null;
    }




}