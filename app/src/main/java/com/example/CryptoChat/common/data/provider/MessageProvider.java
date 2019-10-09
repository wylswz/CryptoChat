package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.models.Message;

import java.util.Date;
import java.util.List;

public abstract class MessageProvider {
    abstract public Message getMessageById(String Id);

    abstract public List<Message> getMessages(String userId, Date from, Date to);

    abstract public List<Message> getMessages(String userId, int limit, int offset);

    abstract public void InsertMessage(Message m);

    abstract public void DropMessageById(String Id);


}
