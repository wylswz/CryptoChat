package com.example.CryptoChat.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.Toast;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.services.AuthenticationManager;
import com.example.CryptoChat.services.FirebaseAPIs;
import com.example.CryptoChat.services.KeyValueStore;
import com.example.CryptoChat.utils.DBUtils;

import java.util.UUID;

public class Signup extends Activity {

    Button button_signup, button_goto_signin;
    String str_username, str_password, str_confirm_password;
    public static EditText editText_username;
    public static EditText editText_password;
    public static EditText editText_confirm_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        button_signup = findViewById(R.id.button_login);
        //button_goto_signin = findViewById(R.id.go_to_signup_page);
        editText_username = (EditText) findViewById(R.id.username);
        editText_password = (EditText) findViewById(R.id.password);
        editText_confirm_password = (EditText) findViewById(R.id.confirm_password);

//        click sign up button:
        button_signup.setOnClickListener(new View.OnClickListener() { //为组件设置点击事件
            @Override
            public void onClick(View v) {
                str_username = editText_username.getText().toString();
                str_password = editText_password.getText().toString();
                str_confirm_password = editText_confirm_password.getText().toString();


//              check whether one of the blank is not typed
                if (str_username.equals("")) {
                    Toast.makeText(Signup.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (str_password.equals("")) {
                    Toast.makeText(Signup.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (str_confirm_password.equals("")) {
                    Toast.makeText(Signup.this, "Please re-enter password", Toast.LENGTH_SHORT).show();
                }

//              check whether the two passwords are same
                else if (!str_password.equals(str_confirm_password)) {
                    Toast.makeText(Signup.this, "wrong password", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO: Store username/password
                    //TODO: Set AuthenticationManager

                    // Clean the database
                    SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(getApplicationContext())).clear();
                    SQLiteMessageProvider.getInstance(DBUtils.getDaoSession(getApplicationContext())).clear();
                    SQLiteUserProvider.getInstance(DBUtils.getDaoSession(getApplicationContext())).clear();

                    KeyValueStore.getInstance().putValue(getApplicationContext(), KeyValueStore.USERNAME, str_username);
                    KeyValueStore.getInstance().putValue(getApplicationContext(), KeyValueStore.PASSWORD, str_password);
                    String Uid = UUID.randomUUID().toString();
                    KeyValueStore.getInstance().putValue(getApplicationContext(), KeyValueStore.UID, Uid);
                    AuthenticationManager.setUid(Uid);
                    Toast.makeText(Signup.this, "Successfully sign up.", Toast.LENGTH_SHORT).show();


                    MainActivity.open(Signup.this);


                }


            }

        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }
}
