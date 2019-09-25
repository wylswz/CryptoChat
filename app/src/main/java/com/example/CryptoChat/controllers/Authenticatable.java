package com.example.CryptoChat.controllers;

import android.content.DialogInterface;
import android.os.Handler;


import android.widget.Button;

import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;


public interface Authenticatable {
    public BiometricPrompt.AuthenticationCallback getCallback();
    public void verifyAuth();
    public void initUI();
    public FragmentActivity getBioActivity();
    public default Handler getHandler(){
        return new Handler();
    }

    public default Executor getExecutor() {
        return  new Executor() {
            @Override
            public void execute(Runnable command) {
                getHandler().post(command);
            }
        };
    }


    public default void showBiometricPrompt(){


        BiometricPrompt.PromptInfo promptInfo =
                new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric login for my app")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Cancel")
                        .build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(getBioActivity(),
                getExecutor(), this.getCallback());

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo);
    }

}
