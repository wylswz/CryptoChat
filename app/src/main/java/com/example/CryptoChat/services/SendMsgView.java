package com.example.CryptoChat.services;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.CryptoChat.R;
import com.example.CryptoChat.services.SendMsg;

// corresponding to UI
public class SendMsgView extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView bodyView;
    public TextView receiverView;


    public SendMsgView(View itemView) {
        super(itemView);
// correspond to UI
        authorView = itemView.findViewById(R.id.messageUserAvatar);
        bodyView = itemView.findViewById(R.id.messageText);
     //   receiverView = itemView.findViewById(R.id.receiver)
    }

    public void bindToMsg(SendMsg sendMsg) {
        //titleView.setText(sendMsg.title);
        authorView.setText(sendMsg.author);
        bodyView.setText(sendMsg.body);
        receiverView.setText(sendMsg.receiver);
    }
}