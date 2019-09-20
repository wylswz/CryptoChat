package com.example.CryptoChat.controllers;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.CryptoChat.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements DialogController.OnFragmentInteractionListener,
ContactController.OnFragmentInteractionListener{

    public static final String EXTRA_MESSAGE = "com.example.CryptoChat.MESSAGE";

    private View getNavFrag() {
        return findViewById(R.id.nav_controller);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.main_nav);
        //nav.setOnNavigationItemSelectedListener(navListener);
        nav.setVisibility(View.VISIBLE);
        nav.setSelectedItemId(R.id.nav_destination_contactController);
        NavController navCtl = Navigation.findNavController(this,R.id.nav_controller);
        NavigationUI.setupWithNavController(nav,navCtl);
        /*
        * Navigating by setting same ID for menu items and nav items
        * */


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
