package com.example.CryptoChat.services;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CryptoChat.common.data.adapters.DialogAdapter;
import com.example.CryptoChat.common.data.adapters.MessageAdapter;
import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.Dialog;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.models.User;
import com.example.CryptoChat.common.data.provider.SQLiteDialogProvider;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.common.data.provider.SQLiteUserProvider;
import com.example.CryptoChat.utils.DBUtils;
import com.example.CryptoChat.utils.rsa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.greendao.DaoException;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FirebaseAPIs {
    private static FirebaseDatabase fbClient = FirebaseDatabase.getInstance();
    private static DatabaseReference mRef = fbClient.getReference();
    private static String TAG = "READFROMFIREBASE";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static HashSet<String> used = new HashSet<>();


    //Read from Firebase
    //listen on message change
    synchronized public static void readMsgFromDB( Context ctx) {

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                RecyclerView.Adapter adapter = AdapterManager.getAdapter();
                Map<String, Object> msgMap = (HashMap)dataSnapshot.child("messages").child(AuthenticationManager.getUid()).getValue(false);
                if (msgMap != null) {

                    for (String k : msgMap.keySet()) {
                        Log.v("FirebaseAPI", k);
                        if (!used.contains(k)) {
                            used.add(k);
                            String id = ((Map<String, String>)msgMap.get(k)).get("id");
                            String senderId = ((Map<String, String>)msgMap.get(k)).get("senderId");
                            String text = ((Map<String, String>)msgMap.get(k)).get("text");
                            String timestamp = ((Map<String, String>)msgMap.get(k)).get("timestamp");
                            Date time = Date.from(Instant.parse(timestamp));
                            Dialog d;
                            Log.v("FirebaseAPIs", msgMap.get(k).toString());
                            try{
                                User author = SQLiteUserProvider.getInstance(DBUtils.getDaoSession(ctx)).getUser(senderId);
                                User receiver = AuthenticationManager.getMe();
                                Message msg = new Message(UUID.randomUUID().toString(),author, receiver,text,time);

                                String plainText;
                                String cipher = msg.getText();
                                String privateKey = KeyValueStore.getInstance().get(ctx,KeyValueStore.PRIVKEY);
                                try {
                                    byte[] decodeData = Base64.getDecoder().decode(cipher.getBytes());
                                    byte[] decode = rsa.decryptByPrivateKey(decodeData, privateKey);
                                    String decodeStr = new String(decode);
                                    plainText=decodeStr;
                                }catch (Exception e){

                                    plainText=e.getMessage();

                                }
                                msg.setText(plainText);


                                SQLiteMessageProvider.getInstance(DBUtils.getDaoSession(ctx)).insertMessage(msg);
                                try{
                                    d = SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(ctx)).getDialogByReceiverId(senderId);
                                    d.setLastMessage(msg);
                                    String openID = AdapterManager.getUserId();
                                    if (msg.getSenderId().equals(openID)) {
                                        d.setUnreadCount(0);
                                    }
                                    else{
                                        d.setUnreadCount(d.getUnreadCount()+1);
                                    }
                                    if(adapter instanceof  DialogAdapter) {
                                        List<Dialog> ds = SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(ctx)).getDialogs();
                                        ((DialogAdapter)adapter).setItems(ds);
                                        adapter.notifyDataSetChanged();
                                    }


                                } catch (ObjectNotExistException e) {
                                    Log.e("FirebaseAPIs",e.getMessage() +"Sender: " + senderId);
                                    d = new Dialog(author.getAlias(),author.getAvatar(),senderId,msg,1);
                                    SQLiteDialogProvider.getInstance(DBUtils.getDaoSession(ctx)).addDialog(d);
                                    if (adapter instanceof DialogAdapter) {
                                        ((DialogAdapter)adapter).addItem(d);
                                        adapter.notifyDataSetChanged();

                                    }
                                }

                                if (adapter == null) {
                                    Log.v("FirebaseAPIs", "Null adapter, skip updating");
                                }
                                else if(adapter instanceof MessageAdapter) {
                                    String openID = AdapterManager.getUserId();
                                    Log.v("FirebaseAPIs", openID);
                                    if (msg.getSenderId().equals(openID)) {
                                        ((MessageAdapter) adapter).addToStart(msg, true);
                                        adapter.notifyDataSetChanged();

                                    }
                                }

                            } catch (DaoException e) {
                                Log.e("FirebaseAPIs", "Message from untrusted user");
                            }

                            mRef.child("messages").child(AuthenticationManager.getUid()).child(id).removeValue();

                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // listen on user change
    public static void readUserFromDB() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> userMap = (HashMap)dataSnapshot.child("users").child(AuthenticationManager.getUid()).getValue();
                // User user = dataSnapshot.getValue(User.class);
                //Log.d(TAG, "Value is: " + user);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // update firebase
    // write message to Firebase
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void writeMsg(Message msg, String fromUserId, String toUserId) {
        HashMap<String, String> msgMap = new HashMap<>();
        msgMap.put("id", msg.getId());
        msgMap.put("senderId", msg.getSenderId());
        msgMap.put("receiverId", msg.getReceiverId());
        msgMap.put("text", msg.getText());
        msgMap.put("timestamp", msg.getCreatedAt().toInstant().toString());
        // fromId, not toId
        // fromId is uid
        mRef.child("messages").child(toUserId).child(msg.getId()).setValue(msgMap);
    }

    //write user to Firebase
    public static void writeUser(User user) {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("name", user.getName());
        mRef.child("users").child(user.getId()).setValue(userMap);
    }

    // update users-friends
    private void addFriend(String userId, String friendId) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mRef.child("friends").push().getKey();

        Map<String, Object> friendUpdates = new HashMap<>();
        friendUpdates.put("/friends/" + key, friendId);
        friendUpdates.put("/users-friends/" + userId + "/" + key, friendId);
        mRef.updateChildren(friendUpdates);
    }

    // callback, delete user data
    public static void deleteUser(User user) {
        String userId = user.getId();
        mRef.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess (Void aVoid){
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure (@NonNull Exception e){
                        // Write failed
                    }
                });
    }




}