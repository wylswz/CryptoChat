package com.example.CryptoChat.services;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START Sendmsg_class]
@IgnoreExtraProperties
public class SendMsg {

    public String uid;
    public String author;
    public String body;
    public String receiver;

    public SendMsg() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public SendMsg(String uid, String author, String body, String receiver) {
        this.uid = uid;
        this.author = author;
        this.body = body;
        this.receiver = receiver;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("body", receiver);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
