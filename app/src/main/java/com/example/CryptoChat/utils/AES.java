package com.example.CryptoChat.utils;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AES {
    private static Cipher cipher;
    private static SecretKey key;
    private static KeyGenerator keygen;
    private static byte[] iv;

    public static void init(){
        try{
            keygen = KeyGenerator.getInstance("AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            key = keygen.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE,key);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String  getKey(){
        byte[] keyEncoded =  key.getEncoded();
        return Base64.getEncoder().encodeToString(keyEncoded);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getIV(){
        return Base64.getEncoder().encodeToString(cipher.getIV());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String msg) {
        byte[] msgByte = Base64.getDecoder().decode(msg);
        return null;
    }

}
