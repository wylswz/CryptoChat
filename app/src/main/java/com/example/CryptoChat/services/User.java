package com.example.CryptoChat.services;

public class User {

    public String username;
    public String email;

    public User(){
    }

    public User(String username, String email){
        this.email = email;
        this.username = username;
    }
}
