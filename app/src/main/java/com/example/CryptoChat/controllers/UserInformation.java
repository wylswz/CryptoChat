package com.example.CryptoChat.controllers;

public class UserInformation {

    private static String username;
    private static String password;
    private static String userid;


    public static String get_username(){
        return username;
    }

    public static String get_password(){
        return password;
    }

    public static String get_userid(){
        return userid;
    }

    public static void set_username(String username){
        UserInformation.username = username;
    }

    public static void set_password(String password){
        UserInformation.password = password;
    }

    public static void set_userid(String userid){
        UserInformation.userid = userid;
    }

}
