package com.example.CryptoChat.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.CryptoChat.R;

public class Login extends Activity {

    Button button_signup, button_goto_signin;
    String str_username, str_password, str_confirm_password;
    private static EditText editText_username;
    private static EditText editText_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button_signup = findViewById(R.id.signup);
        button_goto_signin = findViewById(R.id.go_to_signin_page);
        editText_username =(EditText)findViewById(R.id.username);
        editText_password =(EditText)findViewById(R.id.password);


//        click sign up button:
        button_signup.setOnClickListener(new View.OnClickListener() { //为组件设置点击事件
            @Override
            public void onClick(View v) {
                str_username = editText_username.getText().toString();
                str_password = editText_password.getText().toString();


//                todo: checker whether the username is already in the server database
                boolean username_used = false;


//              check whether one of the blank is not typed
                if(str_username.equals("")){
                    Toast.makeText(Login.this, "Please enter username",Toast.LENGTH_SHORT).show();
                }
                else if(str_password.equals("")){
                    Toast.makeText(Login.this, "Please enter password",Toast.LENGTH_SHORT).show();
                }






//              successfully sign up
                else if(username_used){
                    Toast.makeText(Login.this, "username already occupied, try another one.",Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(Login.this, "correct!", Toast.LENGTH_SHORT).show();


//                    todo: go the next page
                }


            }

        });

        button_goto_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                todo:go to sign in page

//                Toast.makeText(Signup.this, "go to sign in page",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Login.this, Signup.class);
//                startActivity(intent);
            }

        });
    }
}
