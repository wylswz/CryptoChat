package com.example.CryptoChat.services;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterManager {
    private static RecyclerView.Adapter adapter;
    synchronized public static RecyclerView.Adapter getAdapter(){
        return adapter;
    }

    synchronized public static void setAdapter(RecyclerView.Adapter adapter){
        AdapterManager.adapter = adapter;
    }

}
