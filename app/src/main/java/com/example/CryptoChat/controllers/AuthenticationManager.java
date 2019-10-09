package com.example.CryptoChat.controllers;

import java.util.Calendar;
import java.util.Date;

public class AuthenticationManager {
    static private boolean authenticated = false;
    static private Date lastUnlock = Calendar.getInstance().getTime();

    synchronized static public boolean getAuthState() {
        /**
         * Lock after 60 seconds
         */
        if (Calendar.getInstance().getTime().getTime() - lastUnlock.getTime() >= 1000 * 60) {
            lock();
            return false;
        }

        return authenticated;
    }

    synchronized static public void setAuthState(boolean state) {
        authenticated = state;
    }

    synchronized static void lock() {
        authenticated = false;
    }

    synchronized static void unlock() {
        lastUnlock = Calendar.getInstance().getTime();
        authenticated = true;
    }
}
