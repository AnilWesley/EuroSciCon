package com.applications.euroscicon.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.applications.euroscicon.R;
import com.applications.euroscicon.utils.ConstantManager;

import java.util.ArrayList;
import java.util.HashMap;


public class SessionsExpandableListAdapter extends BaseExpandableListAdapter {

    public static ArrayList<ArrayList<HashMap<String, String>>> childItems;
    public static ArrayList<HashMap<String, String>> parentItems;
    private LayoutInflater inflater;
    private Activity activity;

    public SessionsExpandableListAdapter(Activity activity, ArrayList<HashMap<String, String>> parentItems,
                                         ArrayList<ArrayList<HashMap<String, String>>> childItems) {

        this.parentItems = parentItems;
        this.childItems = childItems;
        this.activity = activity;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (childItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup viewGroup) {
        final ViewHolderParent viewHolderParent;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_list_layout_my_tracks, null);

            viewHolderParent = new ViewHolderParent();

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.imageView = convertView.findViewById(R.id.imageView);


            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }


        if (isExpanded) {
            viewHolderParent.imageView.setImageResource(R.drawable.ic_baseline_remove_24);
        } else {
            viewHolderParent.imageView.setImageResource(R.drawable.ic_baseline_add_24);
        }


        ConstantManager.childItems = childItems;
        ConstantManager.parentItems = parentItems;

        viewHolderParent.tvMainCategoryName.setText(parentItems.get(groupPosition).get(ConstantManager.Parameter.COUNTRY_NAME));


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {

        final ViewHolderChild viewHolderChild;
        HashMap<String, String> child = childItems.get(groupPosition).get(childPosition);


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_list_layout_choose_tracks, null);
            viewHolderChild = new ViewHolderChild();

            viewHolderChild.tvSubCategoryName = convertView.findViewById(R.id.tvSubCategoryName);
            viewHolderChild.tvDescription = convertView.findViewById(R.id.tvDescription);


            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }


        if (childPosition==0){
            viewHolderChild.tvDescription.setVisibility(View.VISIBLE);
        }else {
            viewHolderChild.tvDescription.setVisibility(View.GONE);
        }

        String text = Html.fromHtml(child.get(ConstantManager.Parameter.COUNTRY_DESCRIPTION)).toString().trim();
        viewHolderChild.tvDescription.setText(text);

        viewHolderChild.tvSubCategoryName.setText(child.get(ConstantManager.Parameter.CITY_NAME));



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);

    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

    }

    private static class ViewHolderParent {

        TextView tvMainCategoryName;
        ImageView imageView;


    }

    private static class ViewHolderChild {

        TextView tvSubCategoryName;
        TextView tvDescription;


    }


}
