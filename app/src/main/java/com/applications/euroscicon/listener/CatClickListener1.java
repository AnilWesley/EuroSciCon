package com.applications.euroscicon.listener;


import com.applications.euroscicon.models.Categories;

public interface CatClickListener1 {

    void onAddClick(int position, Categories categories);
    void onRemoveClick(int position, Categories categories);

}
