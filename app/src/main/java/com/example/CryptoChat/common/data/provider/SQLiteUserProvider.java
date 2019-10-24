package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.UserDao;
import com.example.CryptoChat.common.data.models.User;

import org.greenrobot.greendao.DaoException;

import java.util.ArrayList;
import org.greenrobot.greendao.query.QueryBuilder;

public class SQLiteUserProvider extends ContactProvider {
    private static SQLiteUserProvider instance = null;
    private UserDao userDao;
    private DaoSession session;

    public static SQLiteUserProvider getInstance(DaoSession session) {

        if (instance == null) {
            synchronized (SQLiteMessageProvider.class) {
                if (instance == null) {
                    instance = new SQLiteUserProvider();
                    instance.userDao = session.getUserDao();
                    instance.session = session;
                }
            }
        }
        return instance;
    }

    @Override
    public User getUser(String Id) throws DaoException {
        return this.userDao.queryBuilder().where(UserDao.Properties.Id.eq(Id)).uniqueOrThrow();
    }

    @Override
    public User getUser(int idx) throws DaoException{
        sortUsers();
        return this.userDao.queryBuilder()
                .offset(idx)
                .limit(1)
                .uniqueOrThrow();
    }

    @Override
    public ArrayList<User> getUsers() {
        QueryBuilder<User> qb = this.userDao.queryBuilder();
        ArrayList<User> users = new ArrayList<User>(qb.list());
        return users;
    }

    @Override
    public void deleteUser(String Id){
        this.userDao.queryBuilder()
                .where(UserDao.Properties.Id.eq(Id))
                .buildDelete().executeDeleteWithoutDetachingEntities();
        session.clear();
    }

    @Override
    public void deleteUser(int idx){
        sortUsers();
        this.userDao.queryBuilder()
                .offset(idx)
                .limit(1)
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        session.clear();
    }

    @Override
    public void sortUsers(){
        this.userDao.queryBuilder()
                .orderAsc(UserDao.Properties.Alias);
    }

    @Override
    public int getCount(){
        return (int) this.userDao.queryBuilder().count();
    }

    public void setUser(User user){
        this.userDao.update(user);
    }

    public void addUser(User user) {
        this.userDao.insert(user);
    }

    public void clear(){
        this.userDao.deleteAll();
    }


}
