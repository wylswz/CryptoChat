package com.example.CryptoChat.common.data.adapters;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MessageAdapter extends MessagesListAdapter {

    public MessageAdapter(String senderId, ImageLoader imageLoader) {
        super(senderId, imageLoader);
    }


}
