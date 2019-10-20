package com.example.CryptoChat.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;

public class NFCKeyExchangeController extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_exchange);
        ImageView nfcLogo = findViewById(R.id.nfc_logo);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        nfcLogo.startAnimation(pulse);
        getSupportActionBar().setTitle("Key Exchange");
    }

    public static void open(Context ctx) {
        Intent intent = new Intent(ctx,NFCKeyExchangeController.class);
        ctx.startActivity(intent);
    }


}
