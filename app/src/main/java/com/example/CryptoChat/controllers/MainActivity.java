package com.example.CryptoChat.controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.services.MessageService;
import com.example.CryptoChat.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogController.OnFragmentInteractionListener,
        ContactListController.OnFragmentInteractionListener, SettingsController.OnFragmentInteractionListener {


    private DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// TODO: After auth, check local userid, if valid, set AuthManager
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.NFC,
                        Manifest.permission.USE_BIOMETRIC,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
        // Check permission

        initUI();
        //hide();

        Intent msgService = new Intent(this, MessageService.class);
        startService(msgService);

        mDaoSession = DBUtils.getDaoSession(this);
        DBUtils.initDB(this);
        SQLiteMessageProvider.getInstance(mDaoSession);
        SQLiteDialogProvider.getInstance(mDaoSession);
        SQLiteUserProvider.getInstance(mDaoSession);

        /*
         * Navigating by setting same ID for menu items and nav items
         * */

        /*
         * Temp
         * */
        String TAG = "Sign";
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /*
         * End Temp
         * */

    }

    public void initUI() {
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.main_nav);
        nav.setVisibility(View.VISIBLE);
        nav.setSelectedItemId(R.id.nav_destination_contactController);
        NavController navCtl = Navigation.findNavController(this, R.id.nav_controller);
        NavigationUI.setupWithNavController(nav, navCtl);
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static void open(Context ctx) {
        Intent i = new Intent(ctx, MainActivity.class);
        ctx.startActivity(i);
    }
}
