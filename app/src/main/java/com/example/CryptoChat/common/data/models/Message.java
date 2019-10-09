package com.example.CryptoChat.common.data.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

@Entity
public class Message implements IMessage,
        MessageContentType.Image, /*this is for default image activity_messages_controller implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    @Index(unique = true)
    private String id;


    @NotNull
    private String text;


    @NotNull
    private String userId;


    private Date createdAt;

    private boolean read;


    @Transient
    private User user;

    @Transient
    private Image image;


    public Message(String id, User user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
        this.read = false;
    }



    @Generated(hash = 637306882)
    public Message() {
    }

    @Generated(hash = 1393013971)
    public Message(String id, @NotNull String text, @NotNull String userId, Date createdAt, boolean read) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.createdAt = createdAt;
        this.read = read;
    }

    @Override
    public String getId() {
        return id;
    }


    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public User getUser() {
        return new User("0", "Name", "avatar", false);
        // UserId ==0 -> message by phone owner
        /* TODO: Add logic: if user id == current user id, then return a fake user with ID = 0
            Otherwise query the user from database. If it does not exist, create a temporary
            unknown user (Maybe alert)
        */
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }


    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }


}
