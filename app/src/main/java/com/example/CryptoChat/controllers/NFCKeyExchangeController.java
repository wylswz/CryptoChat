package com.example.CryptoChat.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;
import com.example.CryptoChat.services.AuthenticationManager;
import com.google.gson.Gson;

public class NFCKeyExchangeController extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_exchange);
        ImageView nfcLogo = findViewById(R.id.nfc_logo);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        nfcLogo.startAnimation(pulse);
        getSupportActionBar().setTitle("Key Exchange");


        Gson msg = new Gson();

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            return;
        }

        mAdapter.setNdefPushMessageCallback(this, this);
    }
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String myId = AuthenticationManager.getUid();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", myId.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    public static void open(Context ctx) {
        Intent intent = new Intent(ctx,NFCKeyExchangeController.class);
        ctx.startActivity(intent);
    }


}
