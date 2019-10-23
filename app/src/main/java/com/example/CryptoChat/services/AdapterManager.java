package com.example.CryptoChat.services;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterManager {
    private static RecyclerView.Adapter adapter;

    private static String userID;

    synchronized public static RecyclerView.Adapter getAdapter(){
        return adapter;
    }

    synchronized  public static String getUserId() {return userID;}

    synchronized public static void setAdapter(RecyclerView.Adapter adapter, String userID){
        AdapterManager.adapter = adapter;
        AdapterManager.userID = userID;
    }

}
