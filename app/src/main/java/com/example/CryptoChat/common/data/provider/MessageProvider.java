package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.models.Message;

import java.util.Date;
import java.util.List;

public abstract class MessageProvider {
    abstract public Message getMessageById(String Id);
    abstract public Message getMessageByPk(Long pk);
    abstract public List<Message> getMessages(Date from, Date to, Long userDbId);
    abstract public List<Message> getMessages(Long userDbId);

    abstract public void InsertMessage(Message m);
    abstract public void DropMessageById(String Id);
    abstract public void DropMessageByPk(Long pk);



}
