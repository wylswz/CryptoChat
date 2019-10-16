package com.example.CryptoChat.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.adapters.UserAdapter;
import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.fake.FakeContactProvider;
import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.models.User;
import com.example.CryptoChat.common.data.provider.ContactProvider;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.utils.DBUtils;
import com.squareup.picasso.Picasso;

public class ContactSettingsController extends AppCompatActivity {

    protected Menu menu;
    protected String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uid = (String) intent.getStringExtra("uid");
        try{
            User u = FakeContactProvider.getInstance().getUser(uid);
            setContentView(R.layout.activity_contact_settings);
            EditText alias = findViewById(R.id.edit_contact_alias_text);
            alias.setText(u.getAlias());

            ImageView iv = (ImageView)findViewById(R.id.edit_contact_image);
            Picasso.get().load(u.getAvatar()).into(iv);
        } catch (ObjectNotExistException e) {

        }


        /*
         * TODO: Fetch user object from provider and set default alias to EditText field
         * */

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
                try{
                    User u = FakeContactProvider.getInstance().getUser(uid);
                    EditText alias = findViewById(R.id.edit_contact_alias_text);
                    u.setAlias(alias.getText().toString());
                    FakeContactProvider.getInstance().setUser(u);
                    // TODO: Update corresponding dialog name
                    Dialog d = SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(this))
                            .getDialogByReceiverId(this.uid);
                    d.setDialogName(u.getAlias());
                    SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(this))
                            .updateDialog(d);

                } catch (ObjectNotExistException e) {
                    Log.e("ContactSettingController", "User Not Exist when confirming " +
                            "contact settings");
                }

                onBackPressed();


                break;
        }
        return true;
    }

    public static void open(Context context, String uid) {
        Intent intent = new Intent(context, ContactSettingsController.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }


}
