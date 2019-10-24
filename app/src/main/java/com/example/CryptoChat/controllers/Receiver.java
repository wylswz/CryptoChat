package com.example.CryptoChat.controllers;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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
import java.util.Timer;
import java.util.TimerTask;

public class Receiver extends AppCompatActivity {

    private TextView mTextView;
    Timer timer;
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
            String success= new String("Add friends successfully!!");

            String messages=new String(message.getRecords()[0].getPayload());
            String[] parts =messages.split("<<<>>>");
            String uid = parts[0];
            String keyPublic= parts[1];
            User u = new User(uid,uid,"",true);
            u.setPubkey(keyPublic);
            try{
                SQLiteUserProvider.getInstance(DBUtils.getDaoSession(getApplicationContext())).addUser(u);
            } catch (SQLiteConstraintException e) {
                Toast.makeText(getApplicationContext(),"Friend already exists",Toast.LENGTH_SHORT).show();
            }

            mTextView.setText(keyPublic);

        } else
            mTextView.setText("Waiting for NDEF Message");

        timer= new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
        Intent intent= new Intent(Receiver.this,MainActivity.class);
        startActivity(intent);
        finish();
                           }
                       },500);

    }
}
