package com.example.CryptoChat.controllers;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.CryptoChat.R;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.utils.DBUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity implements DialogController.OnFragmentInteractionListener,
        ContactListController.OnFragmentInteractionListener, SettingsController.OnFragmentInteractionListener, Authenticatable {


    private DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyAuth();
        initUI();
        hide();

        mDaoSession = DBUtils.getDaoSession(this);
        DBUtils.initDB(this);
        SQLiteMessageProvider.getInstance(mDaoSession);

        /*
         * Navigating by setting same ID for menu items and nav items
         * */
    }

    @Override
    public BiometricPrompt.AuthenticationCallback getCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.v("Auth", "Auth error");
                //TODO: In prod env, should exit the program
                show();

                //System.exit(0);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                AuthenticationManager.unlock();
                show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                AuthenticationManager.lock();

                Log.v("Auth", "Auth failed");
            }
        };
    }

    @Override
    public void verifyAuth() {
        if (!AuthenticationManager.getAuthState()) {
            showBiometricPrompt();
        }
    }

    @Override
    public void initUI() {
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.main_nav);
        nav.setVisibility(View.VISIBLE);
        nav.setSelectedItemId(R.id.nav_destination_contactController);
        NavController navCtl = Navigation.findNavController(this, R.id.nav_controller);
        NavigationUI.setupWithNavController(nav, navCtl);
    }

    @Override
    public FragmentActivity getBioActivity() {
        return MainActivity.this;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        //verifyAuth();
        super.onResume();

    }

    @Override
    public void onStart() {

        super.onStart();
        verifyAuth();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void hide() {
        findViewById(R.id.activity_main_layout).setVisibility(View.INVISIBLE);

    }

    private void show() {
        findViewById(R.id.activity_main_layout).setVisibility(View.VISIBLE);

    }
}
