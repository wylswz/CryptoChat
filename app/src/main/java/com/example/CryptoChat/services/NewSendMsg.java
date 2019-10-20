package com.example.CryptoChat.services;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.CryptoChat.services.SendMsg;
import com.example.CryptoChat.services.User;

import java.util.HashMap;
import java.util.Map;

public class NewSendMsg extends BaseActivity {

    private static final String TAG = "NewMSGActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mAuthorField;
    private EditText mBodyField;
    private EditText mReceiverField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_msg);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
/*** corrspond to UI
        mAuthorField = findViewById(R.id.fieldAuthor);
        mBodyField = findViewById(R.id.fieldBody);
        mReceiverField = findViewById(R.id.fieldReceiver);
        mSubmitButton = findViewById(R.id.fabSubmitMsh);
*/
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMsg();
            }
        });
    }

    private void submitMsg() {
        final String author = mAuthorField.getText().toString();
        final String body = mBodyField.getText().toString();
        final String receiver = mReceiverField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(author)) {
            mAuthorField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Receiver is required
        if (TextUtils.isEmpty(receiver)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-msgs
        setEditingEnabled(false);
        Toast.makeText(this, "SendMessaging...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewSendMsg.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new msg
                            writeNewMsg(userId, user.username, body, receiver);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mAuthorField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        mReceiverField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    // [START write_fan_out]
    private void writeNewMsg(String userId, String username, String body, String receiver) {
        // Create new message at /user-messages/$userid/$messageid and at
        // /messages/$messageid simultaneously
        String key = mDatabase.child("messages").push().getKey();
        SendMsg sendMsg = new SendMsg(userId, username, body, receiver);
        Map<String, Object> sendMsgValues = sendMsg.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/messages/" + key, sendMsgValues);
        childUpdates.put("/users-messages/" + userId + "/" + key, sendMsgValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}