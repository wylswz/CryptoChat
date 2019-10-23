package com.example.CryptoChat.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class KeyValueStore {
    public static final String KEYFILE = "com.example.CryptoChat.KEY";
    public static final String UID = "UID";
    public static final String EMAIL = "EMAIL";

    private static KeyValueStore instance;

    public static KeyValueStore getInstance() {
        if (instance == null) {
            synchronized (KeyValueStore.class) {
                if(instance == null) {
                    instance = new KeyValueStore();
                }
            }
        }
        return instance;
    }

    public void putValue(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(KEYFILE,ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putValueSet(Context ctx, String key, Set<String> set) {
        SharedPreferences sp = ctx.getSharedPreferences(KEYFILE,ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, set);
        editor.commit();
    }

    public String get(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(KEYFILE,ctx.MODE_PRIVATE);
        String res = sp.getString(key,null);
        return res;
    }

    public Set<String> getSet(Context ctx, String key){
        SharedPreferences sp = ctx.getSharedPreferences(KEYFILE,ctx.MODE_PRIVATE);
        Set<String> set = sp.getStringSet(key, null);
        return set;
    }
}
