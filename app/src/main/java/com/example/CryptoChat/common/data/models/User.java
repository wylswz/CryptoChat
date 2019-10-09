package com.example.CryptoChat.common.data.models;

import com.stfalcon.chatkit.commons.models.IUser;

public class User implements IUser {

    private String id;
    private String name;
    private String avatar;
    private String alias;
    private boolean online;

    public User(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
        this.alias = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }
    public boolean isOnline() {
        return online;
    }
}

