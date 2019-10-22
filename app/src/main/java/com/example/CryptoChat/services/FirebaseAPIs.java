package com.example.CryptoChat.services;

import com.example.CryptoChat.common.data.adapters.MessageAdapter;
import com.example.CryptoChat.common.data.models.Message;
import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.controllers.MessageController;
import com.example.CryptoChat.utils.DBUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.*;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import com.example.CryptoChat.common.data.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import android.content.SharedPreferences;

public class FirebaseAPIs {
    private static FirebaseDatabase fbClient = FirebaseDatabase.getInstance();
    private static DatabaseReference mRef = fbClient.getReference();
    private static String TAG = "READFROMFIREBASE";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    
    private static Context a_context;


    //Read from Firebase
    //listen on message change
    public static void readMsgFromDB(MessageAdapter adapter) {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> msgMap = (HashMap)dataSnapshot.child("messages").child(AuthenticationManager.getUid()).getValue(false);
                //if(msg.getReceiverId().equals(AuthenticationManager.getUid())) {
                //    SQLiteMessageProvider.getInstance(null).insertMessage(msg);
                //}

               //saveToLocal(a_context, "Message", msgMap);
                FirebaseDatabase.getInstance().getReference().child("messages").child(AuthenticationManager.getUid()).removeValue();

                //TODO: Notify MessageAdapter for real time update
                //TODO: If adapter is not null, push new messages inside and notify data change

                if (msgMap != null) {
                    Log.d(TAG, "Value is: " + msgMap.toString());
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
    public static void writeMsg(Message msg, String fromUserId, String toUserId) {
        HashMap<String, String> msgMap = new HashMap<>();
        msgMap.put("id", msg.getId());
        msgMap.put("senderId", msg.getSenderId());
        msgMap.put("receiverId", msg.getReceiverId());
        msgMap.put("text", msg.getText());
        // fromId, not toId
        // fromId is uid
        mRef.child("messages").child(fromUserId).child(msg.getId()).setValue(msgMap);
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
    // callback, delete message data
    public static void deleteMsg(Map msg) {
        mRef.child("messages").child(AuthenticationManager.getUid()).setValue(msg)
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
    private static void saveToLocal(Context context,String filename, Map<String, Object> map){
        SharedPreferences.Editor note = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        Iterator<Map.Entry<String, Object>> iterator= map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            note.putString(entry.getKey(), (String) entry.getValue());
        }
        note.commit();
    }

}

