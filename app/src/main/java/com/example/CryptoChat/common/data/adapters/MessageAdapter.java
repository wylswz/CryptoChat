package com.example.CryptoChat.common.data.adapters;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MessageAdapter<MESSAGE extends IMessage> extends MessagesListAdapter<MESSAGE > {

    public MessageAdapter(String senderId, ImageLoader imageLoader) {
        super(senderId, imageLoader);
    }
}
