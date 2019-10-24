package com.example.CryptoChat.common.data.provider;


import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.models.DialogDao;

import org.greenrobot.greendao.DaoException;

import java.util.List;

public class SQLiteDialogProvider {
    public static SQLiteDialogProvider instance;
    private DialogDao dialogDao;
    private DaoSession session;

    synchronized public static SQLiteDialogProvider getInstance(DaoSession session) {
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

    synchronized public void addDialog(Dialog dialog) {
        try{
            Dialog d = dialogDao.queryBuilder()
                    .where(DialogDao.Properties.ReceiverId.eq(dialog.getReceiverId())).unique();
            if (d == null) {
                dialogDao.insert(dialog);
            }

        }catch (SQLiteConstraintException e) {
            // No repeated
        }

    }

    synchronized public List<Dialog> getDialogs() {
        List<Dialog> l = dialogDao.queryBuilder()
                .list();
        return l;
    }

    synchronized public Dialog getDialogById(String Id) throws ObjectNotExistException {
        try{
            Dialog d = dialogDao.queryBuilder()
                    .where(DialogDao.Properties.Id.eq(Id))
                    .uniqueOrThrow();
            return d;
        } catch (DaoException e) {
            throw new ObjectNotExistException("Dialog does not exist");
        }

    }

    synchronized public Dialog getDialogByReceiverId(String receiverId) throws ObjectNotExistException {
        try{
            Dialog d = dialogDao.queryBuilder()
                    .where(DialogDao.Properties.ReceiverId.eq(receiverId)).uniqueOrThrow();
            return d;
        }catch (DaoException e) {
            throw new ObjectNotExistException("Dialog does not exist");
        }

    }

    synchronized public void dropDialog(String dialogId) {
        try {
            dialogDao.queryBuilder()
                    .where(DialogDao.Properties.Id.eq(dialogId)).buildDelete().executeDeleteWithoutDetachingEntities();

        } catch (DaoException e) {
            Log.e("SQLiteDialogProvider", e.getMessage());
        }


    }

    synchronized public void dropDialog(Dialog dialog) {
        dialogDao.delete(dialog);
    }

    synchronized public void updateDialog(Dialog dialog) {
        dialogDao.update(dialog);
    }

    synchronized public void clear() {
        dialogDao.deleteAll();
    }

    synchronized public void incrementUnread(String receiverId, int count) {
        Dialog dialog = dialogDao.queryBuilder()
                .where(DialogDao.Properties.ReceiverId.eq(receiverId))
                .uniqueOrThrow();
        dialog.setUnreadCount(dialog.getUnreadCount() + count);
        updateDialog(dialog);
    }

    synchronized public void resetUnread(String receiverId) {
        Dialog dialog = dialogDao.queryBuilder()
                .where(DialogDao.Properties.ReceiverId.eq(receiverId))
                .uniqueOrThrow();

        dialog.setUnreadCount(0);
        updateDialog(dialog);
    }


}
