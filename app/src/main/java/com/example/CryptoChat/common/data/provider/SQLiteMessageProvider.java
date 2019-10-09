package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.models.MessageDao;

import java.util.Date;
import java.util.List;

public class SQLiteMessageProvider extends MessageProvider {

    private static SQLiteMessageProvider instance = null;
    private MessageDao messageDao;
    private DaoSession session;

    public static SQLiteMessageProvider getInstance(DaoSession session){

        if (instance == null) {
            synchronized (SQLiteMessageProvider.class) {
                if (instance == null) {
                    instance = new SQLiteMessageProvider();
                    instance.messageDao = session.getMessageDao();
                    instance.session = session;
                }
            }
        }
        return instance;
    }

    @Override
    public Message getMessageById(String Id) {
        try{
            List<Message> l = this.messageDao.queryBuilder()
                    .where(MessageDao.Properties.Id.eq(Id))
                    .orderDesc(MessageDao.Properties.CreatedAt)
                    .list();
            if (l.size()>=1) return l.get(0);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return null;
    }


    @Override
    public List<Message> getMessages(String userId,Date from, Date to) {
        List<Message> l = this.messageDao.queryBuilder()
                .where(MessageDao.Properties.UserId.eq(userId))
                .where(MessageDao.Properties.CreatedAt.ge(from))
                .where(MessageDao.Properties.CreatedAt.lt(to))
                .orderDesc(MessageDao.Properties.CreatedAt)
                .list();
        return l;
    }



    public List<Message> getMessages(String userId, int limit, int offset) {
        List<Message> l = this.messageDao.queryBuilder()
                .where(MessageDao.Properties.UserId.eq(userId))
                .orderDesc(MessageDao.Properties.CreatedAt)
                .limit(limit)
                .offset(offset)
                .list();

        return l;
    }

    @Override
    public void InsertMessage(Message m) {
        this.messageDao.insert(m);
    }

    @Override
    public void DropMessageById(String Id) {
        this.messageDao.queryBuilder()
                .where(MessageDao.Properties.Id.eq(Id))
                .buildDelete().executeDeleteWithoutDetachingEntities();
        session.clear();
        // Clear the session cache

    }



}
