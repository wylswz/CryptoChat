package com.example.CryptoChat.common.data.adapters;

import android.content.Context;

import com.example.CryptoChat.common.data.provider.SQLiteMessageProvider;
import com.example.CryptoChat.utils.DBUtils;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MessageAdapter<MESSAGE extends IMessage> extends MessagesListAdapter<MESSAGE > {


    private SQLiteMessageProvider mp;


    public MessageAdapter(String senderId, ImageLoader imageLoader, Context ctx) {
        super(senderId, imageLoader);
        this.mp = SQLiteMessageProvider.getInstance(DBUtils.getDaoSession(ctx));
    }

    @Override
    public void clear(){
        super.clear();
        mp.clear();
    }

    public void delete(MESSAGE m){
        super.delete(m);
        mp.dropMessageById(m.getId());

    }


}
