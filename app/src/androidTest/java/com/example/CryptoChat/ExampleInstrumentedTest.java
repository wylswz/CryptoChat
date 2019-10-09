package com.example.CryptoChat;

import android.content.Context;
import android.net.Uri;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.CryptoChat.common.data.models.DaoMaster;
import com.example.CryptoChat.common.data.models.DaoSession;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.utils.DBUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();

        assertEquals("com.example.CryptoChat", appContext.getPackageName());
    }

    @Test
    public void SQLiteMessageProvider() {
        Context ctx = InstrumentationRegistry.getInstrumentation().getContext();

        //DBUtils.initDB(ctx);
        //Log.d("++++++++++", DBUtils.getDbPath(ctx));

        //DaoSession mDaoSession =new DaoMaster(new DaoMaster.DevOpenHelper(ctx,DBUtils.getDbPath(ctx))
        //        .getWritableDb()).newSession();
        //SQLiteMessageProvider messageProvider = SQLiteMessageProvider.getInstance(mDaoSession);

    }
}
