package com.example.CryptoChat.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.FileUtils;

import com.example.CryptoChat.common.data.models.DaoMaster;
import com.example.CryptoChat.common.data.models.DaoSession;

import java.io.File;
import java.io.IOException;

public class DBUtils {
    private static String dbPath = "DataStore.db";
    public static void initDB(Context ctx){
        File root = ctx.getFilesDir();
        File file = new File(root, dbPath);
        if (!file.exists()) {
            SQLiteDatabase.openOrCreateDatabase(getDbPath(ctx),null);
        }

    }

    public static String getDbPath(Context ctx){
        String root = ctx.getFilesDir().getPath();
        return new Uri.Builder().appendEncodedPath(root).appendEncodedPath(dbPath).build().toString();

    }

    public static DaoSession getDaoSession(Context ctx) {
        return new DaoMaster(new DaoMaster.DevOpenHelper(ctx, DBUtils.getDbPath(ctx)).getWritableDb()).newSession();
    }

}
