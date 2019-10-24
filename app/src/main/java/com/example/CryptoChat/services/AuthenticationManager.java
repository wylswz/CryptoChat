package com.example.CryptoChat.services;

import com.example.CryptoChat.common.data.models.User;

import java.util.Calendar;
import java.util.Date;

public class AuthenticationManager {
    static private boolean authenticated = false;
    static private Date lastUnlock = Calendar.getInstance().getTime();
    static private String Uid="0";

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


    /**
     * Get mac address
     * @return
     */
    synchronized public static String getMac(){

        return "";
    }

    synchronized public static String getAvatar(){
        return "https://i.imgur.com/pv1tBmT.png";
    }

    /**
     * Set my avatar
     */
    synchronized public static void setAvatar(){

    }

    public static User getMe(){
        return new User(AuthenticationManager.getUid(), "Test", AuthenticationManager.getAvatar(), false);
    }

}
