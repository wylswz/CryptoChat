package com.example.CryptoChat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.models.User;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.services.AuthenticationManager;
import com.example.CryptoChat.utils.DBUtils;
import com.google.gson.Gson;

public class Receiver extends AppCompatActivity {

    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0];
            mTextView.setText(new String(message.getRecords()[0].getPayload()));
            String uid = new String(message.getRecords()[0].getPayload());
            User u = new User(uid,uid,"",true);
            SQLiteUserProvider.getInstance(DBUtils.getDaoSession(getApplicationContext())).addUser(u);

        } else
            mTextView.setText("Waiting for NDEF Message");

    }
}
