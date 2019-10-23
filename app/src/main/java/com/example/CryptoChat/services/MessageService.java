package com.example.CryptoChat.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoChat.common.data.adapters.DialogAdapter;
import com.example.CryptoChat.common.data.adapters.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

class MessageUpdate extends Thread {
    public CountDownTimer updateOnline;
    private boolean activated;

    public MessageUpdate(RecyclerView.Adapter adapter, Context ctx){
        activated = true;
        updateOnline = new CountDownTimer(System.currentTimeMillis(), 1000) {

            @Override
            public void onTick(long l) {
                FirebaseAPIs.readMsgFromDB(adapter, ctx);
            }

            @Override
            public void onFinish() {
                if (activated)
                this.start();
            }
        };
    }

    public void notifyStop(){
        activated = false;
    }

    public void notifyResume(){
        activated = true;
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
    private MessageUpdate t;
    private RecyclerView.Adapter adapter;

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
        t = new MessageUpdate(adapter, getApplicationContext());
        t.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        t.notifyResume();
        //TODO: Check if t is running, if not start
        if (!t.isAlive()) {
            t.run();
        }
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }


    //TODO: Stop service

    public void stop(){
        //TODO: Set notifyStop MessageUpdate
        t.notifyStop();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        //TODO: When in message activity, stop, set adapter and start with new adapter
        this.adapter = adapter;
    }


    @Override
    public void onDestroy() {
        t.notifyStop();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

}
