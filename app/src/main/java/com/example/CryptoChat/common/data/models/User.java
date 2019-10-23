package com.example.CryptoChat.common.data.models;

import com.stfalcon.chatkit.commons.models.IUser;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User implements IUser {

    @Id(autoincrement = true)
    private Long pk;
    @Index(unique = true)
    private String id;
    private String name;
    private String avatar;
    private String alias;
    private String pubkey;

    private boolean online;

    public User(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
        this.alias = name;
    }

    @Generated(hash = 504421602)
    public User(Long pk, String id, String name, String avatar, String alias,
                boolean online) {
        this.pk = pk;
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.alias = alias;
        this.online = online;
    }

    @Generated(hash = 586692638)
    public User() {
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

    public Long getPk() {
        return this.pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean getOnline() {
        return this.online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setPubkey(String pubkey){
        this.pubkey = pubkey;
    }

    public String getPubkey(){
        return this.pubkey;
    }
}

