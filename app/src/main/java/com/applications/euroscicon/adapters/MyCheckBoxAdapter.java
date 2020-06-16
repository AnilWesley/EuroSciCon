package com.applications.euroscicon.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;


import com.applications.euroscicon.R;
import com.applications.euroscicon.models.CheckBoxItem;

import java.util.List;

public class MyCheckBoxAdapter extends BaseAdapter {

    private Context context;
    private List<CheckBoxItem> list;

    public MyCheckBoxAdapter(Context c, List<CheckBoxItem> l) {
        context = c;
        list = l;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked(int position) {
        return list.get(position).checked;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.row1, null);

            viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
           /* viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
            viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);*/
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        // viewHolder.icon.setImageDrawable(list.get(position).getItemDrawable());
        viewHolder.checkBox.setChecked(list.get(position).isChecked());

        final String itemStr = list.get(position).getItemString();
        viewHolder.checkBox.setText(itemStr);

        viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size()>0) {

                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    // Toast.makeText(context, itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
                }
            }
        });

        viewHolder.checkBox.setChecked(isChecked(position));

        return rowView;
    }

    static class ViewHolder {
        CheckBox checkBox;
      /*  ImageView icon;
        TextView text;*/
    }
}