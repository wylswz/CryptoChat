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
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public SendMsg() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public SendMsg(String uid, String author, String body) {
        this.uid = uid;
        this.author = author;
        this.body = body;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
