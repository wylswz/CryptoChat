package com.example.CryptoChat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;
import com.example.CryptoChat.utils.AppUtils;

public class ContactSettingsController extends AppCompatActivity {

    protected Menu menu;
    protected String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uid = (String)intent.getStringExtra("uid");
        setContentView(R.layout.activity_contact_settings);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar_edit_contact, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm_edit_contact:
                /*
                 * TODO: Update user info here
                 * */
                onBackPressed();


                break;
        }
        return true;
    }


}
