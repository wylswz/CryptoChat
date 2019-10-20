package com.example.CryptoChat.common.data.provider;


import android.util.Log;

import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.models.DialogDao;

import org.greenrobot.greendao.DaoException;

import java.util.List;

/*
 * TODO: Implement singleton pattern with getInstance() method
 * */
public class SQLiteDialogProvider {
    public static SQLiteDialogProvider instance;
    private DialogDao dialogDao;
    private DaoSession session;

    public static SQLiteDialogProvider getInstance(DaoSession session) {
        if (instance == null) {
            synchronized (SQLiteMessageProvider.class) {
                if (instance == null) {
                    instance = new SQLiteDialogProvider();
                    instance.dialogDao = session.getDialogDao();
                    instance.session = session;
                }
            }
        }
        return instance;
    }

    public void addDialog(Dialog dialog) {
        dialogDao.insert(dialog);
    }

    public List<Dialog> getDialogs() {
        List<Dialog> l = dialogDao.queryBuilder()
                .list();
        return l;
    }

    public Dialog getDialogById(String Id) throws ObjectNotExistException {
        try{
            Dialog d = dialogDao.queryBuilder()
                    .where(DialogDao.Properties.Id.eq(Id))
                    .uniqueOrThrow();
            return d;
        } catch (DaoException e) {
            throw new ObjectNotExistException("Dialog does not exist");
        }


    }

    public Dialog getDialogByReceiverId(String receiverId) throws ObjectNotExistException {
        try{
            Dialog d = dialogDao.queryBuilder()
                    .where(DialogDao.Properties.ReceiverId.eq(receiverId)).uniqueOrThrow();
            return d;
        }catch (DaoException e) {
            throw new ObjectNotExistException("Dialog does not exist");
        }

    }

    public void dropDialog(String dialogId) {
        try {
            dialogDao.queryBuilder()
                    .where(DialogDao.Properties.Id.eq(dialogId)).buildDelete().executeDeleteWithoutDetachingEntities();

        } catch (DaoException e) {
            Log.e("SQLiteDialogProvider", e.getMessage());
        }


    }

    public void dropDialog(Dialog dialog) {
        dialogDao.delete(dialog);
    }

    public void updateDialog(Dialog dialog) {
        dialogDao.update(dialog);
    }

    public void clear() {
        dialogDao.deleteAll();
    }


}
