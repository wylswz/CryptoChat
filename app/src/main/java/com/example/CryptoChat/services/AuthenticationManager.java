package com.example.CryptoChat.services;

import java.util.Calendar;
import java.util.Date;

public class AuthenticationManager {
    static private boolean authenticated = false;
    static private Date lastUnlock = Calendar.getInstance().getTime();
    static private String Uid;

    synchronized static public boolean getAuthState() {
        /**
         * Lock after 60 seconds
         */
        if (Calendar.getInstance().getTime().getTime() - lastUnlock.getTime() >= 1000 * 120) {
            lock();
            return false;
        }

        return authenticated;
    }

    synchronized static public void setAuthState(boolean state) {
        authenticated = state;
    }

    synchronized public static void lock() {
        authenticated = false;
    }

    synchronized public static void unlock() {
        lastUnlock = Calendar.getInstance().getTime();
        authenticated = true;
    }

    synchronized public static String getUid(){
        return Uid;

    }

    synchronized public static void setUid (String uid) {
        Uid = uid;
    }
}