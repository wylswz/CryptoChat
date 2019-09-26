package com.example.CryptoChat.common.data.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Message implements IMessage,
        MessageContentType.Image, /*this is for default image activity_messages_controller implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    @Id
    private String id;

    @Index(unique = true)
    private Long dbId;

    @NotNull
    private String text;

    @NotNull
    private Long userDbId;

    private Date createdAt;



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
    }

    @Generated(hash = 2012611862)
    public Message(String id, Long dbId, @NotNull String text, @NotNull Long userDbId, Date createdAt) {
        this.id = id;
        this.dbId = dbId;
        this.text = text;
        this.userDbId = userDbId;
        this.createdAt = createdAt;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    @Override
    public String getId() {
        return id;
    }

    public Long getDbId(){return dbId;}

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
        return this.user;
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

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public Long getUserDbId() {
        return this.userDbId;
    }

    public void setUserDbId(Long userDbId) {
        this.userDbId = userDbId;
    }


    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }


}
