package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.models.MessageDao;

import org.greenrobot.greendao.AbstractDaoSession;

import java.util.Date;
import java.util.List;

public class SQLiteMessageProvider extends MessageProvider {

    public SQLiteMessageProvider instance = null;
    public MessageDao messageDao;

    public SQLiteMessageProvider getInstance(DaoSession session){

        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new SQLiteMessageProvider();
                    this.messageDao = session.getMessageDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Message getMessageById(String Id) {
        return null;
    }

    @Override
    public Message getMessageByPk(Long pk) {
        return null;
    }

    @Override
    public List<Message> getMessages(Date from, Date to, Long userDbId) {
        return null;
    }

    @Override
    public List<Message> getMessages(Long userDbId) {
        return null;
    }

    @Override
    public void InsertMessage(Message m) {

    }

    @Override
    public void DropMessageById(String Id) {

    }

    @Override
    public void DropMessageByPk(Long pk) {

    }

}
