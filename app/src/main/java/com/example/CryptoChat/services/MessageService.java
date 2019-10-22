package com.example.CryptoChat.services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

class MessageUpdate extends Thread {
    public CountDownTimer updateOnline;


    public MessageUpdate(){
        updateOnline = new CountDownTimer(System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long l) {
                FirebaseAPIs.readMsgFromDB();
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
    }

    @Override
    public void run(){
            updateOnline.start();
    }
}

public class MessageService extends Service {

    private static FirebaseDatabase fbClient = FirebaseDatabase.getInstance();




    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {

            //stopSelf(msg.arg1);
        }
    }

    private DatabaseReference mDatabase;
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
//
//        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
        Thread t = new MessageUpdate();
        t.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }


}
