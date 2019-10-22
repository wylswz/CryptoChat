package com.example.CryptoChat.services;

import com.example.CryptoChat.common.data.models.Friend;
import com.example.CryptoChat.common.data.models.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.example.CryptoChat.common.data.models.User;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAPIs {
    private static FirebaseDatabase fbClient = FirebaseDatabase.getInstance();
    private static DatabaseReference mRef = fbClient.getReference("messages");
    private static String TAG = "READFROMFIREBASE";

    //Read from Firebase
    //listen on message change
    public static void readMsgFromDB() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Message msg = dataSnapshot.getValue(Message.class);
                Log.d(TAG, "Value is: " + msg);
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
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + user);
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
    public static void writeMsg(Context msg, String fromUserId, String toUserId) {
        Context message = msg;
        mRef.child("messages").child(fromUserId).setValue(message);
    }

    //write user to Firebase
    public static void writeUser(User user) {
        User user1 = user;
        String userId = user1.getId();
        mRef.child("users").child(userId).setValue(userId);
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
    public static void delete(User user) {
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
    public static void delete(Message msg) {
        String msgId = msg.getId();
        mRef.child("users").child(msgId).setValue(msg)
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

