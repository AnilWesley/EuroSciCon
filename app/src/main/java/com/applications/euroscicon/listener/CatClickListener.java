package com.applications.euroscicon.listener;

public interface CatClickListener {

    void catlistener(boolean status, int position, String price);

    void eposterlistener(int position, String price);

    void yrflistener(int position, String price);
}
