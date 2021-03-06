package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.models.MessageDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class SQLiteMessageProvider extends MessageProvider {

    private static SQLiteMessageProvider instance = null;
    private MessageDao messageDao;
    private DaoSession session;

    public static SQLiteMessageProvider getInstance(DaoSession session) {

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
    public Message getMessageById(String Id) throws DaoException {

            return this.messageDao.queryBuilder()
                    .where(MessageDao.Properties.Id.eq(Id))
                    .orderDesc(MessageDao.Properties.CreatedAt).uniqueOrThrow();

    }


    @Override
    public List<Message> getMessages(String userId, Date from, Date to) {
        List<Message> l = this.messageDao.queryBuilder()
                .where(MessageDao.Properties.ReceiverId.eq(userId))
                .where(MessageDao.Properties.CreatedAt.ge(from))
                .where(MessageDao.Properties.CreatedAt.lt(to))
                .orderDesc(MessageDao.Properties.CreatedAt)
                .list();
        return l;
    }


    public List<Message> getMessages(String userId, int limit, int offset) {
        QueryBuilder<Message> qb = this.messageDao.queryBuilder();
        List<Message> l = qb.whereOr(MessageDao.Properties.ReceiverId.eq(userId),MessageDao.Properties.SenderId.eq(userId))
                .orderDesc(MessageDao.Properties.CreatedAt)
                .limit(limit)
                .offset(offset)
                .list();


        return l;
    }

    @Override
    public void insertMessage(Message m) {
        this.messageDao.insert(m);
    }

    @Override
    public void dropMessageById(String Id) {
        this.messageDao.queryBuilder()
                .where(MessageDao.Properties.Id.eq(Id))
                .buildDelete().executeDeleteWithoutDetachingEntities();
        session.clear();
        // Clear the session cache

    }

    @Override
    public void dropMessageByUser(String userId) {
        QueryBuilder<Message> qb = this.messageDao.queryBuilder();
        qb.whereOr(MessageDao.Properties.ReceiverId.eq(userId), MessageDao.Properties.SenderId.eq(userId))
                .buildDelete().executeDeleteWithoutDetachingEntities();

        session.clear();
    }

    @Override
    public void clear() {
        this.messageDao.deleteAll();
    }


}
