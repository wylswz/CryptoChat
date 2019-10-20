package com.example.CryptoChat.common.data.models;

import android.util.Log;

import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.services.AuthenticationManager;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

@Entity
public class Message implements IMessage,
        MessageContentType.Image, /*this is for default image activity_messages_controller implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    @Id(autoincrement = true)
    private Long pk;
    @Index(unique = true)
    private String id;
    @NotNull
    private String text;

    @NotNull
    private String receiverId;

    @NotNull
    private String senderId;
    private Date createdAt;
    private Boolean read; // Whether the message has been read
    private String imageUrl;


    @Transient
    private User user;

    @Transient
    private Image image;


    public Message(String id, User author, User receiver, String text) {
        this(id, author,receiver, text, new Date());
    }

    public Message(String id, User user, User receiver, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
        this.read = false;
        this.imageUrl = "";
        this.senderId = user.getId();
        this.receiverId = receiver.getId();
    }


    @Generated(hash = 637306882)
    public Message() {
    }

    @Generated(hash = 363325421)
    public Message(Long pk, String id, @NotNull String text, @NotNull String receiverId, @NotNull String senderId,
            Date createdAt, Boolean read, String imageUrl) {
        this.pk = pk;
        this.id = id;
        this.text = text;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createdAt = createdAt;
        this.read = read;
        this.imageUrl = imageUrl;
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

        if (this.senderId.equals(AuthenticationManager.getUid()) || this.senderId.equals("0")) {
            return AuthenticationManager.getMe();
        } else {
            try{
                User u = SQLiteUserProvider.getInstance(null).getUser(senderId);
                return u;
            } catch (Exception e) {
                Log.e("Message", "User not exist when rendering last sender avatar with id " + senderId + "/" + AuthenticationManager.getUid());
            }
        }
        // The message is neither from phone user
        // Nor contacts in the list
        // May happen when change current userID in debug page
        return AuthenticationManager.getMe();

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


    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getPk() {
        return this.pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }


}
