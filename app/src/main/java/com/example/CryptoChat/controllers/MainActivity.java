package com.example.CryptoChat.controllers;

import android.hardware.biometrics.BiometricPrompt;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.CryptoChat.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.Executor;

public class MainActivity extends FragmentActivity implements DialogController.OnFragmentInteractionListener,
ContactListController.OnFragmentInteractionListener, SettingsController.OnFragmentInteractionListener{

    public static final String EXTRA_MESSAGE = "com.example.CryptoChat.MESSAGE";
    private static final String TAG = MainActivity.class.getName();
    private View getNavFrag() {
        return findViewById(R.id.nav_controller);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //showBiometricPrompt();

        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.main_nav);
        //nav.setOnNavigationItemSelectedListener(navListener);
        nav.setVisibility(View.VISIBLE);
        nav.setSelectedItemId(R.id.nav_destination_contactController);
        NavController navCtl = Navigation.findNavController(this,R.id.nav_controller);
        NavigationUI.setupWithNavController(nav,navCtl);
        /*
        * Navigating by setting same ID for menu items and nav items
        * */


    }

    private static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable r) {
            handler.post(r);
        }
    }

    private Executor getMainThreadExecutor() {
        return new MainThreadExecutor();
    }
    private void showBiometricPrompt() {
        BiometricPrompt.AuthenticationCallback authenticationCallback = getAuthenticationCallback();
        BiometricPrompt bp = new BiometricPrompt.Builder(this).build();
        // Set prompt info
        bp.authenticate(new CancellationSignal(),getMainExecutor(), authenticationCallback);
    }

    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {
        // Callback for biometric authentication result
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                Log.i(TAG, "onAuthenticationSucceeded");

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
